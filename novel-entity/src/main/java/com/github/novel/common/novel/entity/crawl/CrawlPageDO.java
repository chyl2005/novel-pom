package com.github.novel.common.novel.entity.crawl;

import lombok.Data;

import java.util.Date;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:19:44
 * @desc:描述该类的作用
 */
@Data
public class CrawlPageDO {
    private Integer id;
    private Integer siteId;
    private Integer pageTypeId;
    private String url;
    private Date gmtModified;
    private Date gmtCreated;
    private Integer isDel;

}
