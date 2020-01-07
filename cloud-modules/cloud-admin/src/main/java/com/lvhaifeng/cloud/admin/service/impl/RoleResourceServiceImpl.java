package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.RoleResourceMapper;
import com.lvhaifeng.cloud.admin.service.IRoleResourceService;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:26
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RoleResource> pageRoleResourceList(RoleResource roleResource, Integer pageNo, Integer pageSize) {
        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
        Page<RoleResource> page = new Page<>(pageNo, pageSize);
        IPage<RoleResource> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleResource(RoleResource roleResource) {
        EntityUtils.setDefaultValue(roleResource);
        return super.save(roleResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByRoleResourceId(RoleResource roleResource) {
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
    public boolean removeByRoleResourceId(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleResourceIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(ids);
        }
    }

    @Override
    public RoleResource getByRoleResourceId(Serializable id) {
        RoleResource roleResource = super.getById(id);
        if (null == roleResource) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return roleResource;
        }
    }
}
