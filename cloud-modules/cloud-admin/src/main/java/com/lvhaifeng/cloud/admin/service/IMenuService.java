package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Menu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;
import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:30
 */
public interface IMenuService extends IService<Menu> {
    IPage<MenuInfo> findMenuPageList(Menu menu, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createMenu(ResourceInfo resourceInfo);
    boolean alterMenuById(ResourceInfo resourceInfo);
    boolean dropMenuById(Serializable id);
    boolean dropMenuBatch(String ids);
    MenuInfo findMenuById(Serializable id);
    List<MenuInfo> getMenuByRoleId(String id);
    List<MenuInfo> findAllMenus();
}
