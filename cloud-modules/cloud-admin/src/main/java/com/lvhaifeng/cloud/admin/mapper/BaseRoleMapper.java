package com.lvhaifeng.cloud.admin.mapper;

import com.lvhaifeng.cloud.admin.entity.BaseRole;
import com.lvhaifeng.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 */
public interface BaseRoleMapper extends CommonMapper<BaseRole> {
    /**
     * 根据角色ID获取角色列表
     *
     * @author haifeng.lv
     * @date 2019-07-30 16:38
     */
    List<BaseRole> selectRoleListByIds(@Param("roleIds") List<String> roleIds);

    /**
     * 获取角色列表
     *
     * @author haifeng.lv
     * @date 2019-07-30 20:04
     */
    List<BaseRole> selectRoleList(@Param("keyword") String keyword);

    /**
     * 保存角色,返回生成的主键
     *
     * @author haifeng.lv
     * @date 2019-08-01 19:46
     */
    int insertRole(@Param("role") BaseRole role);
}
