package com.github.novel.common.constant;

/**
 * @author:chyl2005
 * @date:17/11/26
 * @time:21:23
 * @desc:描述该类的作用
 */
public enum XPathTypeEnum {

    INNER_TEXT(1, "inner-text"),
    INNER_HTML(2, "html"),
    ATTRIBUTE(3, "属性"),
    REGEX_STATE(4, "正则表达式");

    private Integer code;
    private String name;

    XPathTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public static String getName(Integer code) {
        for (XPathTypeEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static XPathTypeEnum getXPathType(Integer code) {
        for (XPathTypeEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

}
