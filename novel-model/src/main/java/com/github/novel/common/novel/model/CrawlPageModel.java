package com.github.novel.common.novel.model;

import lombok.Data;

import java.util.List;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:10:26
 * @desc:网页抓取模型
 */
@Data
public class CrawlPageModel {

    /**
     * 网页编码
     */
    private String pageCode;

    /**
     * 来源网站
     */
    private String sourceSite;

    /**
     * 需要抓取的网页链接 正则
     */
    private String crawUrl;

    private Integer pageTypeId;


    /**
     * XPath解析
     */
    private List<XPathModel> xPathModels;

}
