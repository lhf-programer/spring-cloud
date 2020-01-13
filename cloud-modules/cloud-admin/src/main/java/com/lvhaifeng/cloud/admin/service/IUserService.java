package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.response.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-13 14:37
 */
public interface IUserService extends IService<User> {
    IPage<User> findUserPageList(User user, Integer pageNo, Integer pageSize, HttpServletRequest req);
    boolean createUser(User user);
    boolean alterUserById(User user);
    boolean dropUserById(Serializable id);
    boolean dropUserBatch(String ids);
    User findUserById(Serializable id);
    UserInfo findUserInfoByToken(String token) throws Exception;
}
