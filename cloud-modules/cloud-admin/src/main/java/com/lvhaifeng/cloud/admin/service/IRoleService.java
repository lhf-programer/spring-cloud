package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:33
 */
public interface IRoleService extends IService<Role> {
    @Override
    boolean save(Role role);
    @Override
    boolean updateById(Role role);
    @Override
    boolean removeById(Serializable id);
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);
    @Override
    Role getById(Serializable id);
}
