package com.github.novel.common.constant;

/**
 * @author:chyl2005
 * @date:17/11/26
 * @time:21:23
 * @desc:描述该类的作用
 */
public enum PageTypeEnum {

    LIST(1, "列表页"),
    UPDATE(2, "更新页"),
    DETAIL(3, "详情页"),
    CHAPTER(4, "章节列表页");

    private Integer code;
    private String name;

    PageTypeEnum(Integer code, String name) {
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
        for (PageTypeEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static PageTypeEnum getXPathType(Integer code) {
        for (PageTypeEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }

}
