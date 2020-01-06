package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Menu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.request.MenuInfo;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:22
 */
public interface IMenuService extends IService<Menu> {
    IPage<Menu> pageMenuList(Menu menu, Integer pageNo, Integer pageSize);
    boolean saveMenu(MenuInfo menuInfo);
    boolean updateByMenuId(MenuInfo menuInfo);
    boolean removeByMenuId(Serializable id);
    boolean removeByMenuIds(Collection<? extends Serializable> ids);
    Menu getByMenuId(Serializable id);
}
