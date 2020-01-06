package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ErrCodeConstant;
import com.lvhaifeng.cloud.admin.constant.ResourceTypeEnum;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.mapper.MenuMapper;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.lvhaifeng.cloud.admin.vo.request.MenuInfo;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-04 16:11
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Resource
    private RoleResourceServiceImpl roleResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MenuInfo menuInfo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuInfo, menu);
        EntityUtils.setDefaultValue(menu);

        if (super.save(menu)) {
            RoleResource roleResource = new RoleResource();
            EntityUtils.setDefaultValue(roleResource);
            roleResource.setDescription(menuInfo.getDescription());
            // 资源类型
            roleResource.setType(ResourceTypeEnum.MENU.getType());
            // 角色 id
            roleResource.setRoleId(menuInfo.getRoleId());
            roleResource.setResourceId(menu.getId());

            return roleResourceService.save(roleResource);
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(MenuInfo menuInfo) {
        Menu menuEntity = baseMapper.selectById(menuInfo.getId());
        if(menuEntity==null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            BeanUtils.copyProperties(menuInfo, menuEntity);
        }
        EntityUtils.setDefaultValue(menuEntity);
        return super.updateById(menuEntity);
    }
}
