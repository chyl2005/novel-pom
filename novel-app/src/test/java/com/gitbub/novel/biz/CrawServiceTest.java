package com.gitbub.novel.biz;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.biz.crawl.CrawlService;
import com.github.novel.common.JsonUtils;
import com.github.novel.common.novel.model.CrawlPageModel;

/**
 * @author:chyl2005
 * @date:17/12/2
 * @time:01:00
 * @desc:描述该类的作用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
public class CrawServiceTest {


    @Autowired
    private CrawlService crawlService;


    @Test
    public void testGetCrawlPageModels(){
        List<CrawlPageModel> crawlPageModels = crawlService.getCrawlPageModels("7kankan");
        String s = JsonUtils.object2Json(crawlPageModels);

    }
}
