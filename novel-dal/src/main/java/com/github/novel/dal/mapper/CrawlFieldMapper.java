package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.entity.crawl.CrawlFieldDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface CrawlFieldMapper {

    void insertOrUpdate(CrawlFieldDO crawlFieldDO);

    void batchInsertOrUpdate(List<CrawlFieldDO> crawlFieldDOs);

    List<CrawlFieldDO> selectByParam(@Param("ids") List<Integer> ids);

    Integer selectByCount(@Param("ids") List<Integer> ids);

    void deleteById(@Param("id") Integer id);
}
