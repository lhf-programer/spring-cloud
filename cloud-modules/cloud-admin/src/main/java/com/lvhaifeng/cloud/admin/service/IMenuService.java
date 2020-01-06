package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.request.MenuInfo;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:17
 */
public interface IMenuService extends IService<Menu> {
    boolean save(MenuInfo menuInfo);
    boolean updateById(MenuInfo menuInfo);
    @Override
    boolean removeById(Serializable id);
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);
    @Override
    Menu getById(Serializable id);
}
