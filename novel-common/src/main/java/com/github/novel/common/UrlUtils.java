package com.github.novel.common;

import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:chyl2005
 * @date:17/11/28
 * @time:16:58
 * @desc:描述该类的作用
 */
public class UrlUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    private UrlUtils() {
    }

    /**
     * 获取网站地址 www.caizige.com
     *
     * @param url
     * @return caizige
     */
    public static String getHostName(String url) {
        try {
            URL curUrl = new URL(url);
            String[] host = curUrl.getHost().split("\\.");
            String hostName = host[host.length - 2];
            return hostName;
        } catch (Exception e) {
            LOGGER.error("getHostName", e);
        }
        throw new RuntimeException("url解析失败");
    }

    /**
     * @param aimUrl
     * @param baseUrl
     * @return
     */
    public static String getFullUrl(String aimUrl, String baseUrl) {
        String rtn = "";
        try {
            if (!aimUrl.toLowerCase().startsWith("http")) {
                URL curUrl = new URL(baseUrl);
                if (aimUrl.startsWith("/")) {
                    rtn = curUrl.getProtocol() + "://" + curUrl.getHost() + aimUrl;
                } else {
                    rtn = curUrl.getProtocol() + "://" + curUrl.getHost() + "/" + aimUrl;
                }
            } else {
                return aimUrl;
            }
        } catch (Exception e) {
            LOGGER.error("getHostName", e);
        }
        return rtn.replace("&amp;", "&");
    }

    public static Boolean isRelatePath(String url) throws Exception {
        if (url.startsWith("http") || url.startsWith("/")
                || url.startsWith("www.")) {
            return false;
        }
        return true;
    }
}
