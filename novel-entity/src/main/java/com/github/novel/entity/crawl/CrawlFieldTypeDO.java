package com.github.novel.entity.crawl;

import lombok.Data;

import java.util.Date;

/**
 * @author:chyl2005
 * @date:17/11/28
 * @time:10:39
 * @desc:抓取字段管理
 */
@Data
public class CrawlFieldTypeDO {

    private Integer id;
    private String javaType;
    private String desc;
    private Date gmtModified;
    private Date gmtCreated;
    private Integer isDel;
}
