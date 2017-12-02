package com.github.novel.common.novel.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.github.novel.common.novel.model.NovelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenyanlong
 * @Description: 爬虫配置管理器
 * @ClassName: ParseConfigManager
 * @date 2016年5月27日 上午11:29:17
 */
public class NovelConfigManager{
    private static final Logger LOGGER = LoggerFactory.getLogger(NovelConfigManager.class);
    private static String classRoot = Thread.currentThread().getContextClassLoader().getResource("").getPath().replaceAll("%20", " ");
    private static final Map<String, NovelConfig> NOVEL_CONFIG_MAP = new HashMap<String, NovelConfig>();

   static {
       initConfig();
   }

    /**
     * @return void    返回类型
     * @throws IOException
     * @throws FileNotFoundException
     * @Description: 初始化配置信息
     * @author chenyanlong
     * @date 2016年5月27日 上午11:28:35
     */
    private static void initConfig() {
        File file = new File(classRoot + "site");
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            NovelConfig parseConfig = new NovelConfig();
            Properties p = new Properties();
            try {
                p.load(new FileInputStream(file2));
                parseConfig.setPageCode(p.getProperty("pageCode"));
                parseConfig.setListPage(p.getProperty("listPage"));
                parseConfig.setUpdatePage(p.getProperty("updatePage"));
                parseConfig.setNovelPage(p.getProperty("novelPage"));
                parseConfig.setChapterPage(p.getProperty("chapterPage"));

                parseConfig.setUpdateNovelPath(p.getProperty("updateNovelPath"));
                parseConfig.setNewChapterTitlePath(p.getProperty("newChapterTitlePath"));

                parseConfig.setNovelName(p.getProperty("novelName"));
                parseConfig.setAuthor(p.getProperty("author"));
                parseConfig.setDesc(p.getProperty("desc"));
                parseConfig.setState(p.getProperty("state"));
                parseConfig.setType(p.getProperty("type"));
                parseConfig.setUpdateDate(p.getProperty("updateDate"));
                parseConfig.setClick(p.getProperty("click"));
                parseConfig.setRecommend(p.getProperty("recommend"));
                parseConfig.setCoverImage(p.getProperty("coverimage"));
                parseConfig.setDirectoryUrl(p.getProperty("directoryUrl"));
                parseConfig.setChapterContent(p.getProperty("chaptercount"));
                parseConfig.setChapterPageNum(p.getProperty("chapterPageNum"));
                parseConfig.setChapterList(p.getProperty("chapterList"));
                parseConfig.setChapterContent(p.getProperty("chapterContent"));
                parseConfig.setContentPageNum(p.getProperty("contentPageNum"));
                parseConfig.setNextPage(p.getProperty("nextPage"));
                String name = file2.getName();
                NOVEL_CONFIG_MAP.put(name.substring(0, name.lastIndexOf(".")), parseConfig);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param url 域名 www.caizige.com
     * @return ParseConfig    返回类型
     * @Description: 获取目标网站配置
     * @author chenyanlong
     * @date 2016年5月27日 上午11:31:10
     */
    public static NovelConfig getParseConfig(String url) {
        String hostName = getSourceSiteKey(url);
        return NOVEL_CONFIG_MAP.get(hostName);
    }

    /**
     * @param url http\://www.23us.com/book/
     * @return String     23us
     * @Description:
     * @author chenyanlong
     * @date 2016年5月27日 下午1:23:19
     */
    public static String getSourceSiteKey(String url) {
        String hostName = "";
        try {
            URL curUrl = new URL(url);
            String[] host = curUrl.getHost().split("\\.");
            hostName = host[host.length - 2];
        } catch (Exception e) {
            LOGGER.error("getSourceSiteKey url {}", url, e);
        }
        return hostName;
    }

}
