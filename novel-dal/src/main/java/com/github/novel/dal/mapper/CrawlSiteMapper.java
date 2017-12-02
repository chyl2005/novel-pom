package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.entity.crawl.CrawlSiteDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface CrawlSiteMapper {
    void insertOrUpdate(CrawlSiteDO crawlSiteDO);

    void batchInsertOrUpdate(List<CrawlSiteDO> crawlSiteDOs);

    CrawlSiteDO  getCrawlSite(@Param("sourceSite") String sourceSite);

    List<CrawlSiteDO> selectByParam(@Param("ids") List<Integer> ids);

    Integer selectByCount(@Param("ids") List<Integer> ids);



    void deleteById(@Param("id") Integer id);
}
