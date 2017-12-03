package com.github.novel.dal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.novel.common.constant.NovelCrawStatusEnum;
import com.github.novel.entity.NovelDO;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:38
 * @desc:小说基础信息
 */
public interface NovelInfoMapper {
    void insertOrUpdate(NovelDO novelDO);

    void batchInsertOrUpdate(List<NovelDO> novelDOs);

    /**
     * 更新小说抓取状态
     * @param bookId
     * @param crawStatus
     */
    void updateCrawStatus(@Param("bookId") String bookId,@Param("crawStatus") NovelCrawStatusEnum crawStatus);

    /**
     * 根据小说ID查询小说信息
     *
     * @param bookId 详情页地址md5
     * @return
     */
    NovelDO selectByBookId(@Param("bookId") String bookId);

    /**
     * 根据章节列表链接查询小说信息
     *
     * @param chapterListUrl
     * @return
     */
    List<NovelDO> selectByChapterUrl(@Param("chapterListUrl") String chapterListUrl);

    List<NovelDO> selectByParam(@Param("siteId") Integer siteId, @Param("pageTypeId") Integer pageTypeId);

    Integer selectByCount(@Param("siteId") Integer siteId, @Param("pageTypeId") Integer pageTypeId);

    void deleteByBookId(@Param("bookId") String bookId);
}
