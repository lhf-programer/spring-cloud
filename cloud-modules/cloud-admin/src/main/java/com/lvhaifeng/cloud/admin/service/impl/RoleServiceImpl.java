package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.mapper.RoleMapper;
import com.lvhaifeng.cloud.admin.service.IRoleService;
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
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-09 14:37
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Role> findRolePageList(Role role, Integer pageNo, Integer pageSize) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        Page<Role> page = new Page<>(pageNo, pageSize);
        IPage<Role> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createRole(Role role) {
        EntityUtils.setDefaultValue(role);
        return super.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterRoleById(Role role) {
        Role roleEntity = baseMapper.selectById(role.getId());
        if(roleEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(role, roleEntity);
        }
        EntityUtils.setDefaultValue(roleEntity);
        return super.updateById(roleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRole(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRoleBatch(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(ids);
        }
    }

    @Override
    public Role findRoleById(Serializable id) {
        Role role = super.getById(id);
        if (null == role) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return role;
        }
    }
}
