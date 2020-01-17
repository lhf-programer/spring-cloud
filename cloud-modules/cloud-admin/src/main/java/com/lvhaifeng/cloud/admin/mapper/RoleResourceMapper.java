package com.lvhaifeng.cloud.admin.mapper;

import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {
    /**
     * @Description 批量插入
     * @Author haifeng.lv
     * @param: roleResources
     * @Date 2020/1/16 10:43
     */
    void insertBatch(List<RoleResource> roleResources);
}
