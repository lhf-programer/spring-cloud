package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface IUserRoleService extends IService<UserRole> {
    IPage<UserRole> findUserRolePageList(UserRole userRole, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createUserRole(UserRole userRole);
    boolean alterUserRoleById(UserRole userRole);
    boolean dropUserRoleById(String id);
    boolean dropUserRoleBatch(String ids);
    UserRole findUserRoleById(String id);
    void insertBatch(List<String> roleIds, String userId);
}
