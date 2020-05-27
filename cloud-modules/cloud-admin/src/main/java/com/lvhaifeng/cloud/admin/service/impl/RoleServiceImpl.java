package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ErrCodeConstant;
import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.lvhaifeng.cloud.admin.mapper.RoleMapper;
import com.lvhaifeng.cloud.admin.service.IRoleResourceService;
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

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleResourceService roleResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Role> findRolePageList(Role role, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<Role> queryWrapper = QueryHelper.initQueryWrapper(role, sortProp, sortType);
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
    public boolean dropRoleById(String id) {
        QueryWrapper queryWrapperUserRole = new QueryWrapper();
        queryWrapperUserRole.eq("role_id", id);
        List<UserRole> userRoles = userRoleService.list(queryWrapperUserRole);
        if (!Collections.isEmpty(userRoles)) {
            throw new BusinessException(ErrCodeConstant.NO_ROLE_PERMIT_ERR);
        }
        QueryWrapper queryWrapperRoleResource = new QueryWrapper();
        queryWrapperRoleResource.eq("role_id", id);
        // 删除对应资源
        roleResourceService.remove(queryWrapperRoleResource);

        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropRoleBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            QueryWrapper queryWrapperUserRole = new QueryWrapper();
            queryWrapperUserRole.in("role_id", Arrays.asList(ids.split(",")));
            List<UserRole> userRoles = userRoleService.list(queryWrapperUserRole);
            if (!Collections.isEmpty(userRoles)) {
                throw new BusinessException(ErrCodeConstant.NO_ROLE_PERMIT_ERR);
            }
            QueryWrapper queryWrapperRoleResource = new QueryWrapper();
            queryWrapperRoleResource.in("role_id", Arrays.asList(ids.split(",")));
            // 删除对应资源
            roleResourceService.remove(queryWrapperRoleResource);

            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public Role findRoleById(String id) {
        Role role = super.getById(id);
        if (null == role) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return role;
        }
    }
}
