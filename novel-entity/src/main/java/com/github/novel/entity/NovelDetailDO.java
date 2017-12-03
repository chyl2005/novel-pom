package com.github.novel.entity;

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

    private Integer id;
    /**
     * bookUrl md5
     */
    private String bookId;
    /**
     * 网络地址
     */
    private String bookUrl;
    /**
     * 小说名字
     */
    private String bookName;
    /**
     * 小说作者
     */
    private String author;
    /**
     * 最新章节
     */
    private String chapterTitle;

    /**
     * 最新章节链接
     */
    private String chapterUrl;

    /**
     * 来源网站
     */
    private String sourceSite;

    /**
     * 0更新，1详情页已抓取
     */
    private Integer status;
    /**
     * 小说更新时间
     */
    private Date updateTime;

    private Date gmtCreated;
    private Date gmtModified;
    /**
     * 是否删除 0 未删除 1已删除
     */
    private Integer isDel;

}
