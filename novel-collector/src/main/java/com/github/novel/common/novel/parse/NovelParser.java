package com.github.novel.common.novel.parse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.novel.common.HttpClientUtils;
import com.github.novel.common.novel.entity.NovelDO;
import com.github.novel.common.novel.model.NovelConfig;
import com.github.novel.common.DateUtils;
import com.github.biz.parse.HtmlCleanerUtils;

public class NovelParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(NovelParser.class);

    public void parse(String url) throws MalformedURLException, IOException, XPatherException {

        NovelConfig parseConfig = NovelConfigManager.getParseConfig(url);
        String content = HttpClientUtils.invokeGet(url, parseConfig.getPageCode());
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode tagNode = cleaner.clean(content);
        if (isUpdatePage(url, parseConfig)) {
            this.getUpdatePage(url, parseConfig, tagNode);
        } else if (isNovelPage(url, parseConfig)) {

        } else if (isChapterPage(url, parseConfig)) {

        }
    }

    public Boolean getUpdatePage(String url, NovelConfig novelConfig, TagNode rootNode) throws XPatherException, MalformedURLException {
        //章节列表节点
        List<TagNode> updateNovelPaths = HtmlCleanerUtils.getChildrens(rootNode, novelConfig.getUpdateNovelPath());
        List<TagNode> newChapterTitlePaths = HtmlCleanerUtils.getChildrens(rootNode, novelConfig.getNewChapterTitlePath());
        for (int i = 0; i < updateNovelPaths.size(); i++) {

            String chapterLink = updateNovelPaths.get(i).getAttributeByName("href");
            chapterLink = getFullUrl(chapterLink, url).replace("&amp;", "&");
            String newChapterTitle = newChapterTitlePaths.get(i).getText().toString();
            LOGGER.info("chapterLink:{}   newChapterTitle : {}", chapterLink, newChapterTitle);

        }

        return true;
    }

    public NovelDO getBaseInfo(NovelConfig novelConfig, String url, String content)
            throws Exception {
        NovelDO novel = new NovelDO();
        // 书名
        String nameStr = HtmlCleanerUtils.getNodeValue(novelConfig.getNovelName(), content);

        // 作者
        String authorStr = HtmlCleanerUtils.getNodeValue(novelConfig.getAuthor(), content);

        if (StringUtils.isBlank(authorStr)) {
            authorStr = "匿名";
        }

        if (nameStr != null && authorStr != null && nameStr.contains(authorStr)
                && !nameStr.equals(authorStr)) {
            nameStr = nameStr.replace(authorStr, "");
            nameStr = nameStr.replaceAll("<[^>]+>", "");
            authorStr = authorStr.replaceAll("<[^>]+>", "");
        }
        authorStr = authorStr.replace(" ", "");
        novel.setAuthor(authorStr);
        novel.setBookName(nameStr);
        // 小说描述
        String descStr = HtmlCleanerUtils.getNodeValue(novelConfig.getDesc(), content);
        if (descStr != null) {
            descStr = descStr.replaceAll("<[^>]+>", "");
        }
        novel.setDescription(descStr);
        // 小说类型
        String typeStr = HtmlCleanerUtils.getNodeValue(novelConfig.getType(), content);
        if (typeStr != null) {
            typeStr = typeStr.replace("<[^>]+>", "");
        }
        novel.setType(typeStr);

        URL curUrl = new URL(url);

        novel.setSourceSite(curUrl.getHost());

        novel.setUrl(url);

        // 小说状态
        String nState = HtmlCleanerUtils.getNodeValue(novelConfig.getState(), content);
        if (StringUtils.isNotBlank(nState) && nState.contains("连载")) {
            novel.setState(0);
        } else if (StringUtils.isNotBlank(nState) && nState.contains("完")) {
            novel.setState(1);
        }

        // 更新时间
        String nUpdateDate = HtmlCleanerUtils.getNodeValue(novelConfig.getUpdateDate(), content);
        if (StringUtils.isNotBlank(nUpdateDate)) {
            novel.setUpdateDate(DateUtils.getDate(nUpdateDate,DateUtils.YMD_FORMAT));
        }else {
            novel.setUpdateDate(new Date());
        }
        String knowledgeType = HtmlCleanerUtils.getNodeValue(novelConfig.getKnowledgeType(), content);
        novel.setKnowledgeType(knowledgeType);

        // 点击
        String nClick = HtmlCleanerUtils.getNodeValue(novelConfig.getClick(), content);
        if (StringUtils.isNotBlank(nClick)) {
            novel.setClick(Long.valueOf(nClick));
        }
        // 推荐
        String nRecommend = HtmlCleanerUtils.getNodeValue(novelConfig.getRecommend(), content);
        if (StringUtils.isNotBlank(nRecommend)) {
            novel.setRecommend(Long.valueOf(nRecommend));
        }

        // 章节列表页url
        String directoryUrl = HtmlCleanerUtils.getNodeValue(novelConfig.getDirectoryUrl(), content);
        if (StringUtils.isNotBlank(directoryUrl)) {
            directoryUrl = getFullUrl(directoryUrl, url);
            directoryUrl = directoryUrl.replace("&amp;", "&");
            novel.setChapterListUrl(directoryUrl);
        }

        return novel;
    }

    private String getFullUrl(String aimUrl, String baseUrl) throws MalformedURLException {
        String rtn = "";

        if (!aimUrl.toLowerCase().startsWith("http")) {
            URL curUrl = new URL(baseUrl);
            if (aimUrl.startsWith("/")) {
                rtn = curUrl.getProtocol() + "://" + curUrl.getHost() + aimUrl;
            } else {
                rtn = curUrl.getProtocol() + "://" + curUrl.getHost() + "/" + aimUrl;
            }
        } else {
            return aimUrl;
        }

        return rtn;
    }

    /**
     * @param url
     * @return Boolean 返回类型
     * @Description: 判断是否是列表页面
     * @author chenyanlong
     * @date 2016年5月27日 下午1:28:20
     */
    public Boolean isUpdatePage(String url, NovelConfig parseConfig) {
        if (null != parseConfig) {
            return url.matches(parseConfig.getUpdatePage());
        }
        return false;
    }

    /**
     * @param url
     * @return Boolean 返回类型
     * @Description: 判断是否是详情页面
     * @author chenyanlong
     * @date 2016年5月27日 下午1:28:20
     */
    public Boolean isNovelPage(String url, NovelConfig parseConfig) {
        if (null != parseConfig) {
            return url.matches(parseConfig.getNovelPage());
        }
        return false;
    }

    /**
     * @param url
     * @return Boolean 返回类型
     * @Description: 判断是否为章节列表页
     * @author chenyanlong
     * @date 2016年5月27日 下午1:28:20
     */
    public Boolean isChapterPage(String url, NovelConfig parseConfig) {
        if (null != parseConfig) {
            return url.matches(parseConfig.getChapterPage());
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        NovelParser novelParser = new NovelParser();
        novelParser.parse("http://www.7kankan.com/files/article/toplastupdate/0/1.htm");

    }
}
