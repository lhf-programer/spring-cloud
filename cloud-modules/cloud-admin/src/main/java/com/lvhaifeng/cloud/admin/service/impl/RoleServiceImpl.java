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
 * @Date: 2020-01-06 14:26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Role> pageRoleList(Role role, Integer pageNo, Integer pageSize) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        Page<Role> page = new Page<>(pageNo, pageSize);
        IPage<Role> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(Role role) {
        EntityUtils.setDefaultValue(role);
        return super.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByRoleId(Role role) {
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
    public boolean removeByRoleId(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(ids);
        }
    }

    @Override
    public Role getByRoleId(Serializable id) {
        Role role = super.getById(id);
        if (null == role) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return role;
        }
    }
}
