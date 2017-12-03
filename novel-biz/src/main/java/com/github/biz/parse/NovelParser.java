package com.github.biz.parse;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.biz.crawl.CrawlService;
import com.github.novel.common.HttpClientUtils;
import com.github.novel.common.JsonUtils;
import com.github.novel.common.MD5Utils;
import com.github.novel.common.UrlUtils;
import com.github.novel.common.constant.PageTypeEnum;
import com.github.novel.common.novel.model.ChapterModel;
import com.github.novel.common.novel.model.CrawlPageModel;
import com.github.novel.dal.mapper.NovelInfoMapper;
import com.github.novel.entity.NovelDO;
import com.github.novel.entity.NovelDetailDO;

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

    @Autowired
    private NovelInfoMapper novelInfoMapper;

    @Override
    public void parse(String url) {
        String sourceSite = UrlUtils.getHostName(url);
        List<CrawlPageModel> pageModels = crawlService.getCrawlPageModels(sourceSite);
        List<CrawlPageModel> crawlPageModel = CrawlPageUtils.getCrawlPageModel(pageModels, url);
        for (CrawlPageModel pageModel : crawlPageModel) {
            String content = HttpClientUtils.invokeGet(url, pageModel.getPageCode());
            if (PageTypeEnum.UPDATE.getCode().equals(pageModel.getPageTypeId())) {
                this.getUpdatePage(pageModel, content);

            } else if (PageTypeEnum.DETAIL.getCode().equals(pageModel.getPageTypeId())) {
                getDetailPage(pageModel, content);

            } else if (PageTypeEnum.CHAPTER.getCode().equals(pageModel.getPageTypeId())) {
                List<NovelDO> novelDOs = novelInfoMapper.selectByChapterUrl(url);
                for (NovelDO novelDO : novelDOs) {

                }
            }
        }
    }

    public void getUpdatePage(CrawlPageModel crawlPageModel, String content) {
        List<Map<String, Object>> results = CrawlPageUtils.getNodeValues(crawlPageModel, content);
        String json = JsonUtils.object2Json(results);
        List<NovelDetailDO> novelDetails = JsonUtils.json2List(json, NovelDetailDO.class);
        for (NovelDetailDO novelDetail : novelDetails) {
            String bookId = MD5Utils.getMD5(novelDetail.getBookUrl());
            novelDetail.setBookId(bookId);
        }
        //插入
        LOGGER.info("getUpdatePage   novelDetails:{}", JsonUtils.object2Json(results));
    }

    public void getDetailPage(CrawlPageModel crawlPageModel, String content) {
        Map<String, Object> resultMap = CrawlPageUtils.getSingleNodeValue(crawlPageModel, content);
        String json = JsonUtils.object2Json(resultMap);
        NovelDO novelDO = JsonUtils.json2Object(json, NovelDO.class);
        String bookId = MD5Utils.getMD5(novelDO.getBookUrl());
        novelDO.setBookId(bookId);
    }

    public void getChapterPage(CrawlPageModel crawlPageModel, String content) {
        List<Map<String, Object>> results = CrawlPageUtils.getNodeValues(crawlPageModel, content);
        String json = JsonUtils.object2Json(results);
        List<ChapterModel> chapters = JsonUtils.json2List(json, ChapterModel.class);
    }

}
