package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:25
 */
public interface IUserService extends IService<User> {
    IPage<User> pageUserList(User user, Integer pageNo, Integer pageSize);
    boolean saveUser(User user);
    boolean updateByUserId(User user);
    boolean removeByUserId(Serializable id);
    boolean removeByUserIds(Collection<? extends Serializable> ids);
    User getByUserId(Serializable id);
}
