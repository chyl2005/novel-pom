package com.github.novel.common.novel.entity.crawl;

import lombok.Data;

import java.util.Date;

/**
 * @author:chyl2005
 * @date:17/11/28
 * @time:10:39
 * @desc: xpath解析是使用的过滤器
 */
@Data
public class CrawlFilterDO {

    private Integer id;
    /**
     * craw_xpath.id
     */
    private Integer xpathId;
    /**
     * 过滤类型
     *
     * @see
     */
    private Integer filterType;
    /**
     * 被替换的内容
     */
    private String regex;
    /**
     * 替换成的内容
     */
    private String replacement;

    /**
     * @see
     */
    private Integer xpathType;

    private Date gmtModified;
    private Date gmtCreated;
    private Integer isDel;
}
