package com.github.novel.common.constant;

/**
 * @author:chyl2005
 * @date:17/11/26
 * @time:21:23
 * @desc:描述该类的作用
 */
public enum XPathFilterTypeEnum {

    TEXT(1, "文本过滤"),
    REGEX(2, "正则表达式");

    private Integer code;
    private String name;

    XPathFilterTypeEnum(Integer code, String name) {
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
        for (XPathFilterTypeEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static XPathFilterTypeEnum getFilterType(Integer code) {
        for (XPathFilterTypeEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

}
