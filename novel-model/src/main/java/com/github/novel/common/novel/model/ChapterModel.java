package com.github.novel.common.novel.model;

import lombok.Data;

import com.github.novel.common.constant.DeleteStatusEnum;

/**
 * @author:chyl2005
 * @date:17/12/3
 * @time:17:15
 * @desc:章节信息
 */
@Data
public class ChapterModel {
    /**
     * 章节ID
     */
    private Integer chapterId;
    /**
     * 网络地址
     */
    private String chapterUrl;
    /**
     * 章节名称
     */
    private String chapterTitle;
    /**
     * 章节内容
     */
    private String content;
    /**
     * 删除状态
     */
    private DeleteStatusEnum deleteStatus;
}
