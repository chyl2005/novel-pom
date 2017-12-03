package com.github.biz.parse;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.novel.common.DateUtils;
import com.github.novel.common.JsonUtils;
import com.github.novel.common.constant.XPathFilterTypeEnum;
import com.github.novel.common.constant.XPathTypeEnum;
import com.github.novel.common.novel.model.CrawlPageModel;
import com.github.novel.common.novel.model.XPathFilterModel;
import com.github.novel.common.novel.model.XPathModel;

/**
 * @author:chenyanlong@meituan.com
 * @date:17/12/3
 * @time:16:23
 * @desc:描述该类的作用
 */
public class CrawlPageUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlPageUtils.class);
    private static HtmlCleaner cleaner = new HtmlCleaner();

    private CrawlPageUtils() {
    }

    /**
     * 返回单一结果
     *
     * @param crawlPageModel
     * @param content
     * @return
     */
    public static Map<String, Object> getSingleNodeValue(CrawlPageModel crawlPageModel, String content) {
        Map<String, Object> resutMap = new HashMap<>(20);
        for (XPathModel xPathModel : crawlPageModel.getXPathModels()) {
            String nodeValue = getSingleNodeValue(xPathModel, content);
            resutMap.put(xPathModel.getFieldName(), nodeValue);
        }
        return resutMap;
    }

    /**
     * 返回列表结果
     * <p>
     * xPathModels 按照抓取结果顺序返回统一个map对象    map key value is XPathModel.fieldName
     *
     * @param crawlPageModel
     * @param content
     * @return
     */
    public static List<Map<String, Object>> getNodeValues(CrawlPageModel crawlPageModel, String content) {
        TagNode rootNode = cleaner.clean(content);
        List<List<Map<String, Object>>> results = new ArrayList<>();
        for (XPathModel xPathModel : crawlPageModel.getXPathModels()) {
            List<Map<String, Object>> nodeValues = getNodeValues(xPathModel, rootNode);
            results.add(nodeValues);
        }
        List<Map<String, Object>> merges = mergeListMap(results);
        return merges;
    }

    public static List<CrawlPageModel> getCrawlPageModel(List<CrawlPageModel> pageModels, String url) {
        List<CrawlPageModel> models = pageModels.stream()
                                                .filter(page -> url.matches(page.getCrawUrl()))
                                                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(models)) {
            return models;
        }
        throw new RuntimeException("未匹配到配置！ url " + url);
    }

    /**
     * 返回单一结果
     *
     * @param xPathModel
     * @param content
     * @return
     */
    private static String getSingleNodeValue(XPathModel xPathModel, String content) {
        //正则匹配
        if (XPathTypeEnum.REGEX_STATE.equals(xPathModel.getXPathType())) {
            return HtmlCleanerUtils.getInfoByRegex(xPathModel.getXpath(), content);
        }
        //xpath
        StringBuilder result = new StringBuilder();
        TagNode rootNode = cleaner.clean(content);
        List<TagNode> tagNodes = HtmlCleanerUtils.getChildrens(rootNode, xPathModel.getXpath());
        for (TagNode tagNode : tagNodes) {
            result.append(getNodeValue(xPathModel, tagNode));
        }
        return result.toString();

    }

    /**
     * 两层List中的map 按顺序合并成一个List
     *
     * @param results
     * @return
     */
    private static List<Map<String, Object>> mergeListMap(List<List<Map<String, Object>>> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> merges = results.get(0);
        for (int i = 1; i < results.size(); i++) {
            List<Map<String, Object>> mapList = results.get(i);
            for (int j = 0; j < mapList.size(); j++) {
                Map<String, Object> valueMap = merges.get(j);
                valueMap.putAll(mapList.get(j));
            }
        }
        LOGGER.info("merges:{}   results : {}", JsonUtils.object2Json(merges), JsonUtils.object2Json(results));
        return merges;
    }

    /**
     * @param xPathModel
     * @param rootNode   根节点
     * @return List<Map<String,String>>
     */
    private static List<Map<String, Object>> getNodeValues(XPathModel xPathModel, TagNode rootNode) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<TagNode> tagNodes = HtmlCleanerUtils.getChildrens(rootNode, xPathModel.getXpath());
        for (TagNode tagNode : tagNodes) {
            HashMap<String, Object> map = new HashMap<>();
            Object nodeValue = getNodeValue(xPathModel, tagNode);
            map.put(xPathModel.getFieldName(), nodeValue);
            result.add(map);
        }
        return result;

    }

    private static Object getNodeValue(XPathModel xPathModel, TagNode tagNode) {
        String result = "";
        if (XPathTypeEnum.INNER_TEXT.equals(xPathModel.getXPathType())) {
            Iterator<?> nodelist = tagNode.getChildren().iterator();
            while (nodelist.hasNext()) {
                Object node = nodelist.next();
                if (node instanceof ContentNode) {
                    result = node.toString();
                    result = result.replaceAll(" ", "").trim().toLowerCase();
                    result = result.replaceAll("<.*?>", "");
                    result = result.replaceAll("<[^>]+>", "");
                }
            }
        } else if (XPathTypeEnum.INNER_HTML.equals(xPathModel.getXPathType())) {
            result = cleaner.getInnerHtml(tagNode);
            result = result.replaceAll(" ", "").trim().toLowerCase();
            result = result.replaceAll("<.*?>", "");
            result = result.replaceAll("<[^>]+>", "");
        } else if (XPathTypeEnum.ATTRIBUTE.equals(xPathModel.getXPathType())) {
            result = tagNode.getAttributeByName(xPathModel.getAttribute());
        } else if (XPathTypeEnum.REGEX_STATE.equals(xPathModel.getXPathType())) {

        }
        //过滤结果
        if (CollectionUtils.isNotEmpty(xPathModel.getFilter())) {
            for (XPathFilterModel xpathFilter : xPathModel.getFilter()) {
                if (StringUtils.isBlank(xpathFilter.getRegex())) {
                    continue;
                }
                if (XPathFilterTypeEnum.TEXT.equals(xpathFilter.getFilterType())) {
                    String replacement = StringUtils.isNotBlank(xpathFilter.getReplacement()) ? xpathFilter.getReplacement() : "";
                    result.replaceAll(xpathFilter.getRegex().toLowerCase(), replacement.toLowerCase());
                } else if (XPathFilterTypeEnum.REGEX.equals(xpathFilter.getFilterType())) {
                    result = HtmlCleanerUtils.getInfoByRegex(result, xpathFilter.getRegex());
                }
            }
        }

        try {

            Class<?> tatgetClass = Class.forName(xPathModel.getJavaType());
            if (tatgetClass.getName().equals(String.class.getName())) {
                return result;
            } else if (tatgetClass.getName().equals(Integer.class.getName())) {

                return getNumber(result);
            } else if (tatgetClass.getName().equals(Long.class.getName())) {
                return getNumber(result);
            } else if (tatgetClass.getName().equals(BigDecimal.class.getName())) {
                return BigDecimal.valueOf(getNumber(result));
            } else if (tatgetClass.getName().equals(Date.class.getName())) {
                Date date = DateUtils.getDate(result, xPathModel.getDateFromat());
                if (!xPathModel.getDateFromat().contains("yyyy")) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    calendar.setTime(date);
                    calendar.set(Calendar.YEAR, year);
                    return calendar.getTime();
                }
                return date;
            }

        } catch (Exception e) {
            LOGGER.error("getNodeValue  抓取结果格式化失败 result {}", result, e);
            return result;
        }
        return result;

    }

    private static Long getNumber(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Long.valueOf(m.replaceAll("").trim());

    }

}
