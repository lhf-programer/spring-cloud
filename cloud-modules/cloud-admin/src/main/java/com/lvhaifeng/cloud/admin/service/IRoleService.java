package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:26
 */
public interface IRoleService extends IService<Role> {
    IPage<Role> pageRoleList(Role role, Integer pageNo, Integer pageSize);
    boolean saveRole(Role role);
    boolean updateByRoleId(Role role);
    boolean removeByRoleId(Serializable id);
    boolean removeByRoleIds(Collection<? extends Serializable> ids);
    Role getByRoleId(Serializable id);
}
