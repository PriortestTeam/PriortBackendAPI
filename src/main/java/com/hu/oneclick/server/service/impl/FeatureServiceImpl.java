package com.hu.oneclick.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.oneclick.common.exception.BaseException;
import com.hu.oneclick.common.security.service.JwtUserServiceImpl;
import com.hu.oneclick.common.security.service.SysPermissionService;
import com.hu.oneclick.common.util.CloneFormatUtil;
import com.hu.oneclick.dao.FeatureDao;
import com.hu.oneclick.dao.FeatureJoinSprintDao;
import com.hu.oneclick.dao.SprintDao;
import com.hu.oneclick.model.entity.Feature;
import com.hu.oneclick.model.domain.dto.FeatureSaveDto;
import com.hu.oneclick.model.param.FeatureParam;
import com.hu.oneclick.server.service.CustomFieldDataService;
import com.hu.oneclick.server.service.FeatureService;
import com.hu.oneclick.server.service.QueryFilterService;
import com.hu.oneclick.server.service.ViewService;
import com.hu.oneclick.model.entity.View;
import com.hu.oneclick.model.entity.OneFilter;
import cn.zhxu.bs.MapSearcher;
import com.hu.oneclick.common.exception.BizException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * @author qingyang
 */
@Service
public class FeatureServiceImpl extends ServiceImpl<FeatureDao, Feature> implements FeatureService {

    private final static Logger logger = LoggerFactory.getLogger(FeatureServiceImpl.class); // Corrected logger class name

    @Resource
    private FeatureDao featureDao;
    @Resource
    private FeatureJoinSprintDao featureJoinSprintDao;
    @Resource
    private SprintDao sprintDao;
    @Resource
    private JwtUserServiceImpl jwtUserService;
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private QueryFilterService queryFilterService;
    @Resource
    private CustomFieldDataService customFieldDataService;
    @Resource
    private ViewService viewService;
    @Resource
    private MapSearcher mapSearcher;

    @Override
    public List<Feature> list(FeatureParam param) {
        if (StrUtil.isNotBlank(param.getViewId())) {
            // View-based query
            View view = viewService.getById(param.getViewId());
            if (view == null) {
                throw new BizException("View not found");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("projectId", param.getProjectId());
            List<List<OneFilter>> filtersList = processAllFilters(view);
            params.putAll(buildSearchParams(filtersList));

            return mapSearcher.searchFeatures(params, filtersList);

        } else {
            // Original query logic
            return baseMapper.selectList(param.getQueryCondition());
        }
    }

    private Map<String, Object> buildSearchParams(List<List<OneFilter>> filtersList){
        Map<String, Object> params = new LinkedHashMap<>();
        //This section is a placeholder - needs proper implementation based on MapSearcher requirements.
        //The original implementation provided no concrete details on how filters were applied.

        return params;
    }

    private List<List<OneFilter>> processAllFilters(View view) {
        List<List<OneFilter>> filterList = new ArrayList<>();
        if (StrUtil.isNotBlank(view.getFilter())) {
            List<OneFilter> filters = JSON.parseArray(view.getFilter(), OneFilter.class);
            if(filters != null) filterList.add(filters);
        }
        if (StrUtil.isNotBlank(view.getParentId())) {
            View parentView = viewService.getById(view.getParentId());
            if (parentView != null) {
                filterList.addAll(processAllFilters(parentView));
            }
        }
        return filterList;
    }



    @Override
    public Feature add(FeatureSaveDto dto) {
        Feature feature = new Feature();
        BeanUtil.copyProperties(dto, feature);
        if (!JSONUtil.isNull(dto.getCustomFieldDatas())) {
            feature.setFeatureExpand(JSONUtil.toJsonStr(dto.getCustomFieldDatas()));
        }
        this.baseMapper.insert(feature);
        return feature;
    }

    @Override
    public Feature edit(FeatureSaveDto dto) {
        Feature entity = this.getByIdAndProjectId(dto.getId(), dto.getProjectId());
        if (null == entity) {
            throw new BaseException(StrUtil.format("故事查询不到。ID：{} projectId：{}", dto.getId(), dto.getProjectId()));
        }
        Feature feature = new Feature();
        BeanUtil.copyProperties(dto, feature);
        if (!JSONUtil.isNull(dto.getCustomFieldDatas())) {
            feature.setFeatureExpand(JSONUtil.toJsonStr(dto.getCustomFieldDatas()));
        }
        this.baseMapper.updateById(feature);
        return feature;
    }

    @Override
    public Feature getByIdAndProjectId(Long id, Long projectId) {
        QueryWrapper<Feature> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(Feature::getId, id)
            .eq(Feature::getProjectId, projectId);
        Feature feature = this.baseMapper.selectOne(queryWrapper);
        return feature;
    }

    @Override
    public Feature info(Long id) {
        Feature feature = baseMapper.selectById(id);
        if (feature == null) {
            throw new BaseException(StrUtil.format("故事查询不到。ID：{}", id));
        }
        return feature;
    }

    @Override
    public void clone(List<Long> ids) {
        List<Feature> featureList = new ArrayList<>();
        for (Long id : ids) {
            Feature feature = baseMapper.selectById(id);
            if (feature == null) {
                throw new BaseException(StrUtil.format("故事查询不到。ID：{}", id));
            }
            Feature issueClone = new Feature();
            BeanUtil.copyProperties(feature, issueClone);
            issueClone.setId(null);
            issueClone.setTitle(CloneFormatUtil.getCloneTitle(feature.getTitle()));
            featureList.add(issueClone);
        }
        this.saveBatch(featureList);
    }

    @Override
    public List<Map<String, String>> getFeatureByTitle(String title, Long projectId) {
        QueryWrapper<Feature> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .like(Feature::getTitle, title)
            .eq(Feature::getProjectId, projectId);
        List<Feature> features = baseMapper.selectList(queryWrapper);

        return features.stream()
            .map(feature -> {
                Map<String, String> map = new HashMap<>();
                map.put("id", feature.getId().toString());
                map.put("title", feature.getTitle());
                return map;
            })
            .collect(Collectors.toList());
    }


    @Override
    public List<Feature> list(LambdaQueryWrapper<Feature> queryWrapper) {
        return featureDao.selectList(queryWrapper);
    }
}
