package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.entity.crawl.CrawlPageDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface CrawlPageMapper {
    void insertOrUpdate(CrawlPageDO crawPageDO);

    void batchInsertOrUpdate(List<CrawlPageDO> crawPageDOs);

    List<CrawlPageDO> selectByParam(@Param("siteId") Integer siteId, @Param("pageTypeId") Integer pageTypeId);

    Integer selectByCount(@Param("siteId") Integer siteId, @Param("pageTypeId") Integer pageTypeId);

    void delete(@Param("siteId") Integer siteId, @Param("pageTypeId") Integer pageTypeId);

    void deleteById(@Param("id") Integer id);
}
