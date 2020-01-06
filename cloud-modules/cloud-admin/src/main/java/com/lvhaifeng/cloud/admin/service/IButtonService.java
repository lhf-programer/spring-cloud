package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:32
 */
public interface IButtonService extends IService<Button> {
    @Override
    boolean save(Button button);
    @Override
    boolean updateById(Button button);
    @Override
    boolean removeById(Serializable id);
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);
    @Override
    Button getById(Serializable id);
}
