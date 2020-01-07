package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Button;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.ButtonMapper;
import com.lvhaifeng.cloud.admin.service.IButtonService;
import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:20
 */
@Service
public class ButtonServiceImpl extends ServiceImpl<ButtonMapper, Button> implements IButtonService {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Button> pageButtonList(Button button, Integer pageNo, Integer pageSize) {
        QueryWrapper<Button> queryWrapper = new QueryWrapper<>();
        Page<Button> page = new Page<>(pageNo, pageSize);
        IPage<Button> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveButton(ResourceInfo resourceInfo) {
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
    public boolean updateByButtonId(ResourceInfo resourceInfo) {
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
    public boolean removeByButtonId(Serializable id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("resource_id", id);
        roleResourceService.remove(queryWrapper);
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByButtonIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.in("resource_id", ids);
            roleResourceService.remove(queryWrapper);
            return super.removeByIds(ids);
        }
    }

    @Override
    public Button getByButtonId(Serializable id) {
        Button button = super.getById(id);
        if (null == button) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return button;
        }
    }
}
