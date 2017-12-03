package com.github.novel.entity.crawl;

import lombok.Data;

import java.util.Date;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:44
 * @desc:描述该类的作用
 */
@Data
public class CrawlSiteDO {
    private Integer id;

    /**
     * 网站编码
     */
    private String pageCode;
    /**
     * 来源
     */
    private String sourceSite;
    /**
     * @see
     */
    private Integer siteType;
    /**
     * 网站主页
     */
    private String url;
    private Date gmtModified;
    private Date gmtCreated;
    private Integer isDel;

}
