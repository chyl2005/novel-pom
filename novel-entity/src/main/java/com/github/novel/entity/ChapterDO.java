/**
 * @Description:
 */
package com.github.novel.entity;

import lombok.Data;

@Data
public class ChapterDO {

    private int cid;// 网络地址
    private String url;// 网络地址
    private String ctitle;// 章节名称
    private String content;// 章节内容
    private byte isok = 0;// 是否存在该章节内容

}
