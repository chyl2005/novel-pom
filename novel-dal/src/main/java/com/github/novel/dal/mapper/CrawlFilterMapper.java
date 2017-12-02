package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.common.novel.entity.crawl.CrawlFilterDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface CrawlFilterMapper {

    void insertOrUpdate(CrawlFilterDO crawlFilterDO);

    void batchInsertOrUpdate(List<CrawlFilterDO> crawlFilterDOs);

    List<CrawlFilterDO> selectByParam(@Param("ids") List<Integer> ids);

    Integer selectByCount(@Param("ids") List<Integer> ids);

    void deleteById(@Param("id") Integer id);
}
