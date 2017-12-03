package com.gitbub.novel.mapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.novel.common.DateUtils;
import com.github.novel.common.JsonUtils;
import com.github.novel.dal.mapper.*;
import com.github.novel.entity.crawl.CrawlFieldDO;

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
    public void testCrawlField() {

        List<CrawlFieldDO> fieldDOs = crawlFieldMapper.selectByParam(Arrays.asList(1));
        JsonUtils.object2Json(fieldDOs);
    }

    @Test
    public void testCrawlFieldAll() {

        List<Map<String, Object>> list = crawlFieldMapper.selectAll();
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Integer) {
                    System.out.println(value);

                } else if (value instanceof String) {
                    System.out.println(value);
                } else if (value instanceof Date) {
                    System.out.println("date  "+DateUtils.getDayOfMonth((Date) value));
                }

            }

        }
    }
}
