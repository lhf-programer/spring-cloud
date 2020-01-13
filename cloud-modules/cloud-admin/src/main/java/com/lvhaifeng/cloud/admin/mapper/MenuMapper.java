package com.lvhaifeng.cloud.admin.mapper;

import com.lvhaifeng.cloud.admin.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:30
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * @Description 根据角色 id查询菜单
     * @Author haifeng.lv
     * @param: roleId 角色 id
     * @param: id 父节点 id
     * @Date 2020/1/6 17:21
     * @return: java.util.List<com.lvhaifeng.cloud.admin.entity.Menu>
     */
    List<MenuInfo> selectMenuByRoleId(@Param("roleId") String roleId, @Param("id") String id);
}
