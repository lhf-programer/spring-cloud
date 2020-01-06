package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:27
 */
public interface IUserRoleService extends IService<UserRole> {
    IPage<UserRole> pageUserRoleList(UserRole userRole, Integer pageNo, Integer pageSize);
    boolean saveUserRole(UserRole userRole);
    boolean updateByUserRoleId(UserRole userRole);
    boolean removeByUserRoleId(Serializable id);
    boolean removeByUserRoleIds(Collection<? extends Serializable> ids);
    UserRole getByUserRoleId(Serializable id);
}
