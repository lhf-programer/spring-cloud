package com.lvhaifeng.cloud.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    /**
     * @Description 查询菜单列表
     * @Author haifeng.lv
     * @param: page 分页
     * @param: menu
     * @Date 2020/1/14 11:34
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.lvhaifeng.cloud.admin.vo.response.MenuInfo>
     */
    IPage<MenuInfo> selectMenuPageList(@Param("page") Page<MenuInfo> page, @Param("menu") Menu menu);
    /**
     * @Description 查询所有菜单
     * @Author haifeng.lv
     * @Date 2020/1/14 14:32
     * @return: java.util.List<com.lvhaifeng.cloud.admin.vo.response.MenuInfo>
     */
    List<MenuInfo> selectAllMenus();
    /**
     * @Description 查询所有菜单
     * @Author haifeng.lv
     * @param: id 角色 id
     * @Date 2020/1/15 17:00
     * @return: java.util.List<com.lvhaifeng.cloud.admin.entity.Menu>
     */
    List<Menu> selectAllMenusByRoleId(@Param("id") String id);
}
