package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Menu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;

import java.util.List;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:30
 */
public interface IMenuService extends IService<Menu> {
    IPage<MenuInfo> findMenuPageList(Menu menu, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createMenu(Menu menu);
    boolean alterMenuById(Menu menu);
    boolean dropMenuById(String id);
    boolean dropMenuBatch(String ids);
    MenuInfo findMenuById(String id);
    List<MenuInfo> getMenuByRoleId(String id);
    List<MenuInfo> getAllMenusByRoleId(String id);
    List<MenuInfo> findAllMenus();
}
