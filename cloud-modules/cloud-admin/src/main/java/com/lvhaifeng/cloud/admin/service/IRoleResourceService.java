package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.request.AddRoleResource;

import java.util.List;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface IRoleResourceService extends IService<RoleResource> {
    IPage<RoleResource> findRoleResourcePageList(RoleResource roleResource, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createRoleResource(RoleResource roleResource);
    void alterRoleResourceByRoleId(AddRoleResource request);
    boolean dropRoleResourceById(String id);
    boolean dropRoleResourceBatch(String ids);
    RoleResource findRoleResourceById(String id);
    void removeByResourceId(List<String> ids);
}
