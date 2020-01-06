package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:34
 */
public interface IUserService extends IService<User> {
    @Override
    boolean save(User user);
    @Override
    boolean updateById(User user);
    @Override
    boolean removeById(Serializable id);
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);
    @Override
    User getById(Serializable id);
}
