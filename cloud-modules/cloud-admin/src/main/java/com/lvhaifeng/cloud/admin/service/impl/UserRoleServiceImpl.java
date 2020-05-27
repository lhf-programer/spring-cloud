package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ErrCodeConstant;
import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.lvhaifeng.cloud.admin.mapper.UserRoleMapper;
import com.lvhaifeng.cloud.admin.service.IRoleService;
import com.lvhaifeng.cloud.admin.service.IUserRoleService;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import com.lvhaifeng.mybatis.query.QueryHelper;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Autowired
    private IRoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<UserRole> findUserRolePageList(UserRole userRole, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<UserRole> queryWrapper = QueryHelper.initQueryWrapper(userRole, sortProp, sortType);
        Page<UserRole> page = new Page<>(pageNo, pageSize);
        IPage<UserRole> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createUserRole(UserRole userRole) {
        EntityUtils.setDefaultValue(userRole);
        return super.save(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterUserRoleById(UserRole userRole) {
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
    public boolean dropUserRoleById(String id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropUserRoleBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public UserRole findUserRoleById(String id) {
        UserRole userRole = super.getById(id);
        if (null == userRole) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return userRole;
        }
    }

    /**
     * @Description 批量插入
     * @Author haifeng.lv
     * @param: roleId
     * @Date 2020/1/17 13:47
     */
    @Override
    public void insertBatch(List<String> roleIds, String userId) {
        List<UserRole> userRoles = new ArrayList<>();
        if (!Collections.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                Role role = roleService.findRoleById(roleId);
                if (null == role) {
                    throw new BusinessException(ErrCodeConstant.NO_ROLE_ERR);
                }
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(role.getId());
                EntityUtils.setDefaultValue(userRole);
                userRoles.add(userRole);
            });
        }
        baseMapper.insertBatch(userRoles);
    }
}
