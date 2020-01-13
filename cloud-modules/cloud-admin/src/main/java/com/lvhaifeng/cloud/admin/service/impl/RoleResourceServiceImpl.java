package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.RoleResourceMapper;
import com.lvhaifeng.cloud.admin.service.IRoleResourceService;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.query.QueryGenerator;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:24
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RoleResource> findRoleResourcePageList(RoleResource roleResource, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<RoleResource> queryWrapper = QueryGenerator.initQueryWrapper(roleResource, sortProp, sortType);
        Page<RoleResource> page = new Page<>(pageNo, pageSize);
        IPage<RoleResource> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createRoleResource(RoleResource roleResource) {
        EntityUtils.setDefaultValue(roleResource);
        return super.save(roleResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterRoleResourceById(RoleResource roleResource) {
        RoleResource roleResourceEntity = baseMapper.selectById(roleResource.getId());
        if(roleResourceEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(roleResource, roleResourceEntity);
        }
        EntityUtils.setDefaultValue(roleResourceEntity);
        return super.updateById(roleResourceEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRoleResourceById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRoleResourceBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public RoleResource findRoleResourceById(Serializable id) {
        RoleResource roleResource = super.getById(id);
        if (null == roleResource) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return roleResource;
        }
    }
}
