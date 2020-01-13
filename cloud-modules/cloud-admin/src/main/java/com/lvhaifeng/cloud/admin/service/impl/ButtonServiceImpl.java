package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Button;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.ButtonMapper;
import com.lvhaifeng.cloud.admin.service.IButtonService;
import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.query.QueryGenerator;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-13 14:20
 */
@Service
public class ButtonServiceImpl extends ServiceImpl<ButtonMapper, Button> implements IButtonService {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Button> findButtonPageList(Button button, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<Button> queryWrapper = QueryGenerator.initQueryWrapper(button, req.getParameterMap());
        Page<Button> page = new Page<>(pageNo, pageSize);
        IPage<Button> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createButton(ResourceInfo resourceInfo) {
        Button button = new Button();
        BeanUtils.copyProperties(resourceInfo, button);
        EntityUtils.setDefaultValue(button);

        if (super.save(button)) {
            RoleResource roleResource = new RoleResource();
            EntityUtils.setDefaultValue(roleResource);
            roleResource.setDescription(resourceInfo.getDescription());
            // 资源类型
            roleResource.setType(ResourceTypeEnum.BUTTON.getType());
            // 角色 id
            roleResource.setRoleId(resourceInfo.getRoleId());
            roleResource.setResourceId(button.getId());

            return roleResourceService.save(roleResource);
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterButtonById(ResourceInfo resourceInfo) {
        Button buttonEntity = baseMapper.selectById(resourceInfo.getId());
        if(buttonEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(resourceInfo, buttonEntity);
        }
        EntityUtils.setDefaultValue(buttonEntity);
        return super.updateById(buttonEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropButtonById(Serializable id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("resource_id", id);
        roleResourceService.remove(queryWrapper);
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropButtonBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.in("resource_id", Arrays.asList(ids.split(",")));
            roleResourceService.remove(queryWrapper);
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public Button findButtonById(Serializable id) {
        Button button = super.getById(id);
        if (null == button) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return button;
        }
    }
}
