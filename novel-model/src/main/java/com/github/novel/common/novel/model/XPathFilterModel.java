package com.github.novel.common.novel.model;

import lombok.Data;

import com.github.novel.common.constant.XPathFilterTypeEnum;

/**
 * @author:chyl2005
 * @date:17/11/27
 * @time:10:40
 * @desc:xpath解析过滤
 */
@Data
public class XPathFilterModel {

    /**
     * 过滤器类型
     */
    private XPathFilterTypeEnum filterType;

    /**
     * 被替换的内容
     */
    private String regex;
    /**
     * 替换成的内容
     */
    private String replacement;

}
