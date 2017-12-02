package com.github.novel.common.constant;

/**
 * @author:chyl2005
 * @date:17/11/26
 * @time:21:23
 * @desc:描述该类的作用
 */
public enum CrawlSiteTypeEnum {

    NOVEL(1, "小说"),
    VIDEO(2, "视屏");


    private Integer code;
    private String name;

    CrawlSiteTypeEnum(Integer code, String name) {
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
        for (CrawlSiteTypeEnum statusEnum : values()) {
            if (statusEnum.code.equals(code) ) {
                return statusEnum.name;
            }
        }
        return null;
    }

}
