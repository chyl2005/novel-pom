package com.github.novel.common.constant;

/**
 * @author:chenyanlong@meituan.com
 * @date:17/12/3
 * @time:15:11
 * @desc:书等级
 */
public enum NovelCrawStatusEnum {

    DONE(0, "更新完成"),
    UPDATE(1, "更新"),
    UPDATE_ALL(2, "重新抓取");

    private Integer code;
    private String name;

    NovelCrawStatusEnum(Integer code, String name) {
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
        for (NovelCrawStatusEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static NovelCrawStatusEnum getXPathType(Integer code) {
        for (NovelCrawStatusEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
}
