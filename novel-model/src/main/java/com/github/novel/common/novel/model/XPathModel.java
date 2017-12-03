package com.github.novel.common.novel.model;

import lombok.Data;

import java.util.List;
import com.github.novel.common.constant.XPathTypeEnum;

/**
 * @author:chyl2005
 * @date:17/11/26
 * @time:21:35
 * @desc:xpath解析模型
 */
@Data
public class XPathModel {

    /**
     * 字段名字
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String javaType;
    /**
     * xpath
     */
    private String xpath;
    /**
     * xpath 类型
     */
    private XPathTypeEnum xPathType;
    /**
     * XPathTypeEnum.attribute 时使用
     */
    private String attribute;

    private String dateFromat;
    /**
     * 过滤器
     */
    private List<XPathFilterModel> filter;

}
