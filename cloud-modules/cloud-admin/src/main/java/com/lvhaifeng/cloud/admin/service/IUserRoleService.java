package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:34
 */
public interface IUserRoleService extends IService<UserRole> {
    @Override
    boolean save(UserRole userRole);
    @Override
    boolean updateById(UserRole userRole);
    @Override
    boolean removeById(Serializable id);
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);
    @Override
    UserRole getById(Serializable id);
}
