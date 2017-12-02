package com.github.novel.common.novel.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author:chyl2005
 * @date:17/11/26
 * @time:20:26
 * @desc:小说更新信息
 */
@Data
public class NovelDetailDO {

    /**
     * 章节列表页url
     */
    private String chapterLink;
    /**
     * 来源网址
     */
    private String sourceSite;
    /**
     * 新入库0 抓去过1
     */
    private String status;

    private Date gmtCreated;
    private Date gmtModified;
    /**
     * 是否删除 0 未删除 1已删除
     */
    private Date isDel;

}
