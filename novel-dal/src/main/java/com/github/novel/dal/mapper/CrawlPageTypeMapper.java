package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.entity.crawl.CrawlPageTypeDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface CrawlPageTypeMapper {
    void insertOrUpdate(CrawlPageTypeDO crawlPageTypeDO);

    void batchInsertOrUpdate(List<CrawlPageTypeDO> crawlPageTypeDOs);

    List<CrawlPageTypeDO> selectByParam(@Param("ids") List<Integer> typeIds);

    Integer selectByCount(@Param("ids") List<Integer> typeIds);

    void deleteById(@Param("id") Integer id);
}
