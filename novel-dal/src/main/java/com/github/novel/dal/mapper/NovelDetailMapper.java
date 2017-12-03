package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.entity.NovelDetailDO;
import com.github.novel.entity.crawl.CrawlPageDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:描述该类的作用
 */
public interface NovelDetailMapper {



    void insertOrUpdate(NovelDetailDO novelDetailDO);

    void batchInsertOrUpdate(List<NovelDetailDO> novelDetailDOs);

    List<NovelDetailDO> selectByParam(@Param("sourceSite") Integer sourceSite, @Param("status") Integer status);

    Integer selectByCount(@Param("siteId") Integer siteId, @Param("pageTypeId") Integer pageTypeId);


    void deleteByBookId(@Param("bookId") Integer bookId);
}
