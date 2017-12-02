package com.github.novel.common.novel.entity.crawl;

import lombok.Data;

import java.util.Date;

/**
 * @author:chyl2005
 * @date:17/11/28
 * @time:10:39
 * @desc:描述该类的作用
 */
@Data
public class CrawlXpathDO {

    private Integer id;
    private Integer pageId;
    private String xpath;
    /**
     * crawl_field.id
     */
    private Integer fieldId;
    /**
     * @see com.github.novel.entity.constant.XPathTypeEnum
     */
    private Integer xpathType;
    private String attribute;
    private Date gmtModified;
    private Date gmtCreated;
    private Integer isDel;
}
