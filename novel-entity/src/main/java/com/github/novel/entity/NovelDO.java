/**
 * @Description:
 */
package com.github.novel.entity;

import lombok.Data;

import java.util.Date;
import com.github.novel.common.constant.NovelCrawStatusEnum;

@Data
public class NovelDO {

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
     * 章节列表页url
     */
    private String chapterListUrl;
    /**
     * 小说状态，0连载 1完成
     *
     * @NovelStatusEnum
     */
    private Integer status;

    /**
     * 内容即章节
     * 0更新完成，1更新部分，2全部更新(重新抓取全部章节)
     *
     * @see NovelCrawStatusEnum
     */
    private Integer crawStatus;
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
    private String bookType;
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
     * 小说更新时间
     */
    private Date updateTime;

    private Date gmtCreated;
    private Date gmtModified;
    /**
     * 是否删除 0 未删除 1已删除
     */
    private Date isDel;

}
