package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.lvhaifeng.cloud.admin.mapper.UserRoleMapper;
import com.lvhaifeng.cloud.admin.service.IUserRoleService;
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
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:27
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<UserRole> pageUserRoleList(UserRole userRole, Integer pageNo, Integer pageSize) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        Page<UserRole> page = new Page<>(pageNo, pageSize);
        IPage<UserRole> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserRole(UserRole userRole) {
        EntityUtils.setDefaultValue(userRole);
        return super.save(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByUserRoleId(UserRole userRole) {
        UserRole userRoleEntity = baseMapper.selectById(userRole.getId());
        if(userRoleEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(userRole, userRoleEntity);
        }
        EntityUtils.setDefaultValue(userRoleEntity);
        return super.updateById(userRoleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByUserRoleId(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByUserRoleIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            return super.removeByIds(ids);
        }
    }

    @Override
    public UserRole getByUserRoleId(Serializable id) {
        UserRole userRole = super.getById(id);
        if (null == userRole) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return userRole;
        }
    }
}
