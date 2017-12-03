package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.entity.crawl.CrawlFieldTypeDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface CrawlFieldTypeMapper {

    void insertOrUpdate(CrawlFieldTypeDO crawlFieldTypeDO);

    void deleteById(@Param("id") Integer id);

    List<CrawlFieldTypeDO> selectAll();
}
