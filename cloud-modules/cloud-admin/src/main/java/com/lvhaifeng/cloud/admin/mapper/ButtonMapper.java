package com.lvhaifeng.cloud.admin.mapper;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:28
 */
public interface ButtonMapper extends BaseMapper<Button> {
    /**
     * @Description 查询所有按钮通过角色 id
     * @Author haifeng.lv
     * @param: roleId 角色 id
     * @param: menuId 菜单 id
     * @Date 2020/1/15 17:04
     * @return: java.util.List<com.lvhaifeng.cloud.admin.entity.Button>
     */
    List<Button> selectAllButtonsByRoleId(@Param("roleId") String roleId, @Param("menuId") String menuId);
}
