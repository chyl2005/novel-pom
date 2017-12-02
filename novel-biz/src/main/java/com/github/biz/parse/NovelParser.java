package com.github.biz.parse;

import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.biz.crawl.CrawlService;
import com.github.novel.common.constant.PageTypeEnum;
import com.github.novel.common.HttpClientUtils;
import com.github.novel.common.JsonUtils;
import com.github.novel.common.UrlUtils;
import com.github.novel.common.novel.model.CrawlPageModel;
import com.github.novel.entity.NovelDO;
import com.github.novel.common.novel.model.XPathModel;

/**
 * @author:chyl2005
 * @date:17/11/28
 * @time:16:44
 * @desc:小说抓取
 */
@Component("NovelParse")
public class NovelParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(NovelParser.class);
    @Autowired
    private CrawlService crawlService;

    @Override
    public void parse(String url) {
        String sourceSite = UrlUtils.getHostName(url);
        List<CrawlPageModel> pageModels = crawlService.getCrawlPageModels(sourceSite);
        CrawlPageModel crawlPageModel = getCrawlPageModel(pageModels, sourceSite);
        String content = HttpClientUtils.invokeGet(url, crawlPageModel.getPageCode());
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode rootNode = cleaner.clean(content);
        if (PageTypeEnum.UPDATE.getCode().equals(crawlPageModel.getPageTypeId())) {
            this.getUpdatePage(crawlPageModel, rootNode);

        } else if (PageTypeEnum.DETAIL.getCode().equals(crawlPageModel.getPageTypeId())) {

        } else if (PageTypeEnum.CHAPTER.getCode().equals(crawlPageModel.getPageTypeId())) {

        }

    }

    public void getUpdatePage(CrawlPageModel crawlPageModel, TagNode rootNode) {
        List<List<Map<String, String>>> results = new ArrayList<>();
        for (XPathModel xPathModel : crawlPageModel.getXPathModels()) {
            List<Map<String, String>> nodeValues = this.getNodeValues(xPathModel, rootNode);
            results.add(nodeValues);
        }
        List<Map<String, String>> merges = mergeListMap(results);
        List<NovelDO> novelDOs = new ArrayList<>();
        for (Map<String, String> merge : merges) {
            String json = JsonUtils.object2Json(merge);
            NovelDO novelDO = JsonUtils.json2Object(json, NovelDO.class);
            novelDOs.add(novelDO);
        }
        //插入
        LOGGER.info("getUpdatePage   novelDOs:{}", JsonUtils.object2Json(novelDOs));
    }

    /**
     * 两层List中的map 按顺序合并成一个List
     *
     * @param results
     * @return
     */
    private List<Map<String, String>> mergeListMap(List<List<Map<String, String>>> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Collections.emptyList();
        }
        List<Map<String, String>> merges = results.get(0);
        for (int i = 1; i < results.size(); i++) {
            List<Map<String, String>> mapList = results.get(i);
            for (int j = 0; j < mapList.size(); j++) {
                Map<String, String> valueMap = merges.get(j);
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
    private List<Map<String, String>> getNodeValues(XPathModel xPathModel, TagNode rootNode) {
        List<Map<String, String>> result = new ArrayList<>();
        List<TagNode> tagNodes = HtmlCleanerUtils.getChildrens(rootNode, xPathModel.getXpath());
        for (TagNode tagNode : tagNodes) {
            HashMap<String, String> map = new HashMap<>();
            String nodeValue = getNodeValue(xPathModel, tagNode);
            map.put(xPathModel.getFieldName(), nodeValue);
        }
        return result;

    }

    private String getNodeValue(XPathModel xPathModel, TagNode tagNode) {

        return null;

    }

    private CrawlPageModel getCrawlPageModel(List<CrawlPageModel> pageModels, String url) {
        for (CrawlPageModel pageModel : pageModels) {
            if (url.matches(pageModel.getCrawUrl())) {
                return pageModel;
            }
        }
        throw new RuntimeException("未匹配到配置！ url " + url);
    }

}
