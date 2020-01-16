package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface IRoleResourceService extends IService<RoleResource> {
    IPage<RoleResource> findRoleResourcePageList(RoleResource roleResource, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createRoleResource(RoleResource roleResource);
    boolean alterRoleResourceById(RoleResource roleResource);
    boolean dropRoleResourceById(Serializable id);
    boolean dropRoleResourceBatch(String ids);
    RoleResource findRoleResourceById(Serializable id);
}
