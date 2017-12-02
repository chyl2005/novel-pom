package com.github.novel.common.novel.entity.crawl;

import lombok.Data;

import java.util.Date;

/**
 * @author:chyl2005
 * @date:17/11/28
 * @time:10:39
 * @desc:抓取字段管理
 */
@Data
public class CrawlFieldDO {

    private Integer id;
    private String fieldName;
    private String desc;
    private Date gmtModified;
    private Date gmtCreated;
    private Integer isDel;
}
