package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.common.novel.entity.crawl.CrawlXpathDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface CrawlXpathMapper {


    void insertOrUpdate(CrawlXpathDO crawlXpathDO);

    void batchInsertOrUpdate(List<CrawlXpathDO> crawlXpathDOs);

    List<CrawlXpathDO> selectByPageIds(@Param("pageIds") List<Integer> pageIds);

    Integer selectByCount(@Param("pageIds") List<Integer> pageIds);



    void deleteById(@Param("id") Integer id);
}
