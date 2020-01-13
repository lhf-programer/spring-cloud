package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.mapper.RoleMapper;
import com.lvhaifeng.cloud.admin.service.IRoleService;
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
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Role> findRolePageList(Role role, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<Role> queryWrapper = QueryGenerator.initQueryWrapper(role, sortProp, sortType);
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
    public boolean dropRoleById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRoleBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
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
