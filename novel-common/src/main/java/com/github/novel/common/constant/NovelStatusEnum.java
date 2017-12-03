package com.github.novel.common.constant;

/**
 * @author:chenyanlong@meituan.com
 * @date:17/12/3
 * @time:15:11
 * @desc:书等级
 */
public enum NovelStatusEnum {

    /**
     * 完结
     */
    DONE(0, "完结"),
    /**
     * 连载中
     */
    DONING(1, "连载");

    private Integer code;
    private String name;

    NovelStatusEnum(Integer code, String name) {
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
        for (NovelStatusEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static NovelStatusEnum getXPathType(Integer code) {
        for (NovelStatusEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
}
