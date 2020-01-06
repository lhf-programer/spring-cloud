package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:26
 */
public interface IRoleResourceService extends IService<RoleResource> {
    IPage<RoleResource> pageRoleResourceList(RoleResource roleResource, Integer pageNo, Integer pageSize);
    boolean saveRoleResource(RoleResource roleResource);
    boolean updateByRoleResourceId(RoleResource roleResource);
    boolean removeByRoleResourceId(Serializable id);
    boolean removeByRoleResourceIds(Collection<? extends Serializable> ids);
    RoleResource getByRoleResourceId(Serializable id);
}
