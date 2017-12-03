package com.github.novel.entity.crawl;

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
     *
     */
    private Integer xpathType;
    private String attribute;
    private String dateFormat;
    private Date gmtModified;
    private Date gmtCreated;
    private Integer isDel;
}
