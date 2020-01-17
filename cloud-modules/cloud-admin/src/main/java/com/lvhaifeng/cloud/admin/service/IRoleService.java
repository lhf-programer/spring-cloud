package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface IRoleService extends IService<Role> {
    IPage<Role> findRolePageList(Role role, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createRole(Role role);
    boolean alterRoleById(Role role);
    boolean dropRoleById(String id);
    boolean dropRoleBatch(String ids);
    Role findRoleById(String id);
}
