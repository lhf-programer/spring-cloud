package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Role;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-09 14:37
 */
public interface IRoleService extends IService<Role> {
    IPage<Role> findRolePageList(Role role, Integer pageNo, Integer pageSize, HttpServletRequest req);
    boolean createRole(Role role);
    boolean alterRoleById(Role role);
    boolean dropRole(Serializable id);
    boolean dropRoleBatch(Collection<? extends Serializable> ids);
    Role findRoleById(Serializable id);
}
