package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:33
 */
public interface IRoleResourceService extends IService<RoleResource> {
    @Override
    boolean save(RoleResource roleResource);
    @Override
    boolean updateById(RoleResource roleResource);
    @Override
    boolean removeById(Serializable id);
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);
    @Override
    RoleResource getById(Serializable id);
}
