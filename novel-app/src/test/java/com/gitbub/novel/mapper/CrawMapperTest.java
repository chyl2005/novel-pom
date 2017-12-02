package com.gitbub.novel.mapper;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.novel.entity.crawl.CrawlFieldDO;
import com.github.novel.dal.mapper.*;

/**
 * @author:chyl2005
 * @date:17/12/2
 * @time:10:34
 * @desc:描述该类的作用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
public class CrawMapperTest {
    @Autowired
    private CrawlFieldMapper crawlFieldMapper;
    @Autowired
    private CrawlFilterMapper crawlFilterMapper;
    @Autowired
    private CrawlPageMapper crawlPageMapper;
    @Autowired
    private CrawlPageTypeMapper crawlPageTypeMapper;
    @Autowired
    private CrawlSiteMapper crawlSiteMapper;
    @Autowired
    private CrawlXpathMapper crawlXpathMapper;


    @Test
    public void testCrawlField(){
        List<CrawlFieldDO> fieldDOs = crawlFieldMapper.selectByParam(Arrays.asList(1));
    }
}
