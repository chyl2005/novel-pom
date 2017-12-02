package com.github.biz.crawl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.novel.common.constant.XPathFilterTypeEnum;
import com.github.novel.common.constant.XPathTypeEnum;
import com.github.novel.common.novel.model.CrawlPageModel;
import com.github.novel.common.novel.model.XPathModel;
import com.github.novel.common.novel.model.XPathFilterModel;
import com.github.novel.dal.mapper.*;
import com.github.novel.entity.crawl.*;

/**
 * @author:chyl2005
 * @date:17/11/28
 * @time:15:12
 * @desc:描述该类的作用
 */
@Service
public class CrawlService {

    @Autowired
    private CrawlFieldMapper crawlFieldMapper;
    @Autowired
    private CrawlFilterMapper crawlFilterMapper;
    @Autowired
    private CrawlPageMapper crawlPageMapper;
    @Autowired
    private CrawlPageTypeMapper crawlPageTypeMapper;
    @Autowired
    private CrawlSiteMapper crawlSiteMapper;
    @Autowired
    private CrawlXpathMapper crawlXpathMapper;

    /**
     * 获取网页抓取模型
     *
     * @param sourceSite
     * @return
     */
    public List<CrawlPageModel> getCrawlPageModels(String sourceSite) {
        List<CrawlPageModel> crawlPageModels = new ArrayList<>();
        CrawlSiteDO crawlSite = crawlSiteMapper.getCrawlSite(sourceSite);
        List<CrawlPageDO> crawlPageDOs = crawlPageMapper.selectByParam(crawlSite.getId(), null);
        List<Integer> crawPageIds = crawlPageDOs.stream().map(crawlPageDO -> crawlPageDO.getId()).collect(Collectors.toList());

        List<CrawlXpathDO> crawlXpathDOs = crawlXpathMapper.selectByPageIds(crawPageIds);
        List<Integer> xpathIds = crawlXpathDOs.stream().map(xpath -> xpath.getId()).collect(Collectors.toList());
        List<Integer> fieldIds = crawlXpathDOs.stream().map(xpath -> xpath.getFieldId()).collect(Collectors.toList());
        List<CrawlFieldDO> fields = crawlFieldMapper.selectByParam(fieldIds);
        Map<Integer, CrawlFieldDO> fieldMap = fields.stream().collect(Collectors.toMap(field -> field.getId(), field -> field));
        List<CrawlFilterDO> crawFilters = crawlFilterMapper.selectByParam(xpathIds);
        //xpathId->CrawlFilterDO
        Map<Integer, List<CrawlFilterDO>> filterMap = crawFilters.stream().collect(Collectors.groupingBy(filter -> filter.getXpathId()));
        //pageId->xpaths
        Map<Integer, List<CrawlXpathDO>> xpathMap = crawlXpathDOs.stream().collect(Collectors.groupingBy(xpath -> xpath.getPageId()));

        for (CrawlPageDO crawlPageDO : crawlPageDOs) {
            CrawlPageModel crawlPageModel = new CrawlPageModel();
            crawlPageModel.setPageCode(crawlSite.getPageCode());
            crawlPageModel.setSourceSite(crawlSite.getSourceSite());
            crawlPageModel.setCrawUrl(crawlPageDO.getUrl());
            crawlPageModel.setPageTypeId(crawlPageDO.getPageTypeId());
            List<CrawlXpathDO> xpathDOs = xpathMap.get(crawlPageDO.getId());
            List<XPathModel> xPathModels = getXPathModels(xpathDOs, fieldMap, filterMap);
            crawlPageModel.setXPathModels(xPathModels);
            crawlPageModels.add(crawlPageModel);
        }
        return crawlPageModels;
    }

    /**
     * @param xpathDOs
     * @param fieldMap  id->CrawlFieldDO
     * @param filterMap xpathId->CrawlFilterDO
     * @return
     */
    private List<XPathModel> getXPathModels(List<CrawlXpathDO> xpathDOs, Map<Integer, CrawlFieldDO> fieldMap, Map<Integer, List<CrawlFilterDO>> filterMap) {
        if (CollectionUtils.isEmpty(xpathDOs)) {
            return Collections.emptyList();
        }
        List<XPathModel> xPathModels = new ArrayList<>();
        for (CrawlXpathDO xpathDO : xpathDOs) {
            XPathModel xPathModel = new XPathModel();
            xPathModel.setXpath(xpathDO.getXpath());
            xPathModel.setAttribute(xpathDO.getAttribute());
            CrawlFieldDO crawlFieldDO = fieldMap.get(xpathDO.getFieldId());
            if (crawlFieldDO != null) {
                xPathModel.setFieldName(crawlFieldDO.getFieldName());
            }
            xPathModel.setXPathType(XPathTypeEnum.getXPathType(xpathDO.getXpathType()));
            List<XPathFilterModel> xPathFilters = getXPathFilters(filterMap.get(xpathDO.getId()));
            xPathModel.setFilter(xPathFilters);
            xPathModels.add(xPathModel);
        }

        return xPathModels;
    }

    private List<XPathFilterModel> getXPathFilters(List<CrawlFilterDO> crawlFilterDOs) {
        List<XPathFilterModel> filterModels = new ArrayList<>();
        if (CollectionUtils.isEmpty(crawlFilterDOs)) {
            return filterModels;
        }
        for (CrawlFilterDO crawlFilterDO : crawlFilterDOs) {
            XPathFilterModel filterModel = new XPathFilterModel();
            XPathFilterTypeEnum filterType = XPathFilterTypeEnum.getFilterType(crawlFilterDO.getFilterType());
            filterModel.setFilterType(filterType);
            filterModel.setRegex(crawlFilterDO.getRegex());
            filterModel.setReplacement(crawlFilterDO.getReplacement());
            filterModels.add(filterModel);
        }
        return filterModels;
    }
}
