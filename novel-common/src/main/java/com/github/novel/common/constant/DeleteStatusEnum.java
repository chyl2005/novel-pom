package com.github.novel.common.constant;

/**
 * @author:chenyanlong@meituan.com
 * @date:17/12/3
 * @time:15:11
 * @desc:书等级
 */
public enum DeleteStatusEnum {

    /**
     * 未删除
     */
    NOT_DEL(0, "未删除"),
    /**
     * 已删除
     */
    DEL(1, "已删除");

    private Integer code;
    private String name;

    DeleteStatusEnum(Integer code, String name) {
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
        for (DeleteStatusEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static DeleteStatusEnum getXPathType(Integer code) {
        for (DeleteStatusEnum statusEnum : values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
}
