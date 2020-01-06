package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.request.MenuInfo;
import org.apache.poi.ss.formula.functions.T;

/**
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-04 16:11
 */
public interface IMenuService extends IService<Menu> {
    boolean save(MenuInfo menuInfo);
    boolean updateById(MenuInfo menuInfo);
}
