package com.github.biz.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.novel.common.novel.model.XPathModel;
import com.github.novel.common.constant.XPathTypeEnum;

/**
 * @author:chyl2005
 * @date:17/11/26
 * @time:19:59
 * @desc:描述该类的作用
 */
public class HtmlCleanerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlCleanerUtils.class);

    private static HtmlCleaner cleaner = new HtmlCleaner();

    private HtmlCleanerUtils() {
    }

    public static List<TagNode> getChildrens(TagNode rootNode, String xpath) {
        List<TagNode> results = new ArrayList<>();
        try {
            Object[] tagNodes = rootNode.evaluateXPath(xpath);

            for (Object tagNode : tagNodes) {
                if (tagNode instanceof TagNode) {
                    TagNode node = (TagNode) tagNode;
                    results.add(node);
                }
            }
        } catch (Exception e) {
            LOGGER.error("getTagNodes ", e);
        }
        return results;
    }

    public static String getNodeValue(String content, String xPath) {
//        String rtn = "";
//        if (StringUtils.isBlank(xPath)) {
//            return rtn;
//        }
//        XPathModel xPathModel = getXPathModel(xPath);
//        try {
//            if (XPathTypeEnum.INNER_TEXT.getCode().equals(xPathModel.getxPathType())) {
//                rtn = getInnerText(xPathModel.getXpath(), xPathModel.getFilter(), content);
//            } else if (XPathTypeEnum.ATTRIBUTE.getCode().equals(xPathModel.getxPathType())) {
//                rtn = getAttribute(xPathModel.getXpath(), xPathModel.getAttribute(), xPathModel.getFilter(), content);
//            } else if (XPathTypeEnum.REGEX_STATE.getCode().equals(xPathModel.getxPathType())) {
//                // 用正则表达式进行数据匹配
//                Pattern filterPattern = getPattern(xPathModel.getXpath());
//                rtn = getInfoByRegex(content, filterPattern, 1);
//            }
//        } catch (Exception e) {
//            LOGGER.error("getNodeValue  ",e);
//        }

//        return rtn;

        return "";

    }

    public static String getAttribute(String xpath, String attrName, String filter, String content) throws Exception {
        String result = "";
        TagNode rootNode = cleaner.clean(content);
        List<TagNode> tagNodes = getChildrens(rootNode, xpath);
        for (TagNode tagNode : tagNodes) {
            result = tagNode.getAttributeByName(attrName);
            if (filter != null) {
                result = getInfoByRegex(result, getPattern(filter), 1);
            }
        }
        return result;
    }

    public static String getInnerText(String xpath, String filter, String content) throws Exception {

        String result = "";
        Object[] objects = null;
        StringBuilder sb = null;
        sb = new StringBuilder();
        TagNode rootNode = cleaner.clean(content);
        List<TagNode> tagNodes = getChildrens(rootNode, xpath);
        for (TagNode tagNode : tagNodes) {
            String str = cleaner.getInnerHtml(tagNode);
            if (str != null) {
                str = str.replaceAll("<.*?>", "");
            }
            sb.append(str);
        }
        result = sb.toString();
        if (filter != null) {
            if (filter.startsWith("@@@")) {
                filter = filter.substring(3);
                String[] rep = filter.split("___");
                if (rep.length == 1) {
                    String[] arrRep = rep[0].split("&&&");
                    for (int i = 0; i < arrRep.length; i++) {
                        // 不区分大小写进行替换
                        result = result.toLowerCase().replaceAll(
                                arrRep[i].toLowerCase(), "");
                    }
                } else if (rep.length >= 2) {
                    String[] arrRep = rep[0].split("&&&");
                    String[] arrRepDest = rep[1].split("&&&");
                    for (int i = 0; i < arrRep.length; i++) {
                        // 不区分大小写进行替换
                        result = result.toLowerCase().replaceAll(
                                arrRep[i].toLowerCase(),
                                arrRepDest.length > i ? arrRepDest[i] : "");
                    }
                }
            } else {

                String[] filters = filter.split("@@@");

                Pattern filterPattern = getPattern(filters[0]);
                result = getInfoByRegex(result, filterPattern, 1);

                if (filters.length >= 2) {
                    String[] rep = filters[1].split("___");
                    if (rep.length == 1) {
                        String[] arrRep = rep[0].split("&&&");
                        for (int i = 0; i < arrRep.length; i++) {
                            result = result.replaceAll(arrRep[i], "");
                        }
                    } else if (rep.length >= 2) {
                        String[] arrRep = rep[0].split("&&&");
                        String[] arrRepDest = rep[1].split("&&&");
                        for (int i = 0; i < arrRep.length; i++) {
                            result = result.replaceAll(arrRep[i],
                                                       arrRepDest.length > i ? arrRepDest[i] : "");
                        }
                    }
                }
            }
        }

        return result;
    }



    /**
     * !!!分割 0:path,1:type,2:filter,
     * !!!分割 0:path,1:type,2:attrName,3:filter
     *
     * @param xpath
     * @return
     */
    private static XPathModel getXPathModel(String xpath) {
        XPathModel xPathModel = new XPathModel();
        if (StringUtils.isBlank(xpath)) {
            return xPathModel;
        }
        String[] arr_nodePath = xpath.split("!!!");
        if (arr_nodePath.length < 2) {
            throw new RuntimeException("the invalid paramters");
        }
        String path = arr_nodePath[0].trim();
        int type = Integer.parseInt(arr_nodePath[1].trim());
        String attrName = null;
        String filter = null;
        if (arr_nodePath.length >= 3) {
            if (XPathTypeEnum.ATTRIBUTE.getCode().equals(type)) {
                attrName = arr_nodePath[2].trim();
                if (arr_nodePath.length == 4) {
                    filter = arr_nodePath[3].trim();
                }
            } else {
                filter = arr_nodePath[2].trim();
            }
        }
        if (XPathTypeEnum.INNER_TEXT.getCode().equals(type)) {
            xPathModel.setXPathType(XPathTypeEnum.INNER_TEXT);
        } else if (XPathTypeEnum.INNER_HTML.getCode().equals(type)) {
            xPathModel.setXPathType(XPathTypeEnum.INNER_HTML);
        } else if (XPathTypeEnum.ATTRIBUTE.getCode().equals(type)) {
            xPathModel.setXPathType(XPathTypeEnum.ATTRIBUTE);
        } else if (XPathTypeEnum.REGEX_STATE.getCode().equals(type)) {
            xPathModel.setXPathType(XPathTypeEnum.REGEX_STATE);
        }
        xPathModel.setAttribute(attrName);
       // xPathModel.setFilter(filter);
        xPathModel.setXpath(path);
        return xPathModel;

    }

    public static Pattern getPattern(String pattern) {
        return Pattern.compile(pattern, Pattern.DOTALL);
    }

    /**
     * 获取正则内容
     *
     * @param content
     * @param pattern
     * @return
     * @throws Exception
     */
    public static String getInfoByRegex(String content, String pattern) {
        return getInfoByRegex(content,getPattern(pattern),1);
    }

    /**
     * 获取正则内容
     *
     * @param content
     * @param pattern
     * @param groupNo
     * @return
     * @throws Exception
     */
    public static String getInfoByRegex(String content, Pattern pattern, int groupNo)  {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(groupNo);
        }
        return "";
    }

}
