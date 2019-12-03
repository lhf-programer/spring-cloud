package com.lvhaifeng.cloud.admin.mapper;

import com.lvhaifeng.cloud.admin.entity.BaseUserRole;
import com.lvhaifeng.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 */
public interface BaseUserRoleMapper extends CommonMapper<BaseUserRole> {

    /**
     * 通过用户Ids删除角色用户
     * @author haifeng.lv
     * @date 2019-08-01 20:09
     */
    void  delRoleUserByUserIds(@Param("userIdList") List<String> userIds);
}
