/**
 * @Description:
 */
package com.github.novel.common.novel.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NovelDO {

    /**
     * 网络地址
     */
    private String url;
    /**
     * 小说名字
     */
    private String bookName;
    /**
     * 小说作者
     */
    private String author;
    /**
     * 小说状态，0连载 1完成
     */
    private Integer state;
    /**
     * 点击量
     */
    private Long click;
    /**
     * 推荐量
     */
    private Long recommend;
    /**
     * 小说章节数量
     */
    private Integer chapterCount;
    /**
     * 小说简介
     */
    private String description;
    /**
     * 小说的分类，玄幻魔法 、古代穿越
     */
    private String type;
    /**
     * 文学分类 传统文学、 网络小说
     */
    private String knowledgeType;
    /**
     * 来源网站
     */
    private String sourceSite;
    /**
     *
     */
    private String coverUrl;
    /**
     *
     */
    private String coverPath;
    /**
     * 章节列表页url
     */
    private String chapterListUrl;
    /**
     * 小说更新时间
     */
    private Date updateDate;

    private Date gmtCreated;
    private Date gmtModified;
    /**
     * 是否删除 0 未删除 1已删除
     */
    private Date isDel;

}
