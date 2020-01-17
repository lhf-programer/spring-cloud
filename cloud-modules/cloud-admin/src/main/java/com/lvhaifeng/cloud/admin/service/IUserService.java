package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.response.UserInfo;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:27
 */
public interface IUserService extends IService<User> {
    IPage<UserInfo> findUserPageList(User user, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createUser(UserInfo userInfo);
    boolean alterUserById(UserInfo userInfo);
    boolean dropUserById(String id);
    boolean dropUserBatch(String ids);
    UserInfo findUserById(String id);
    UserInfo findUserInfoByToken(String token) throws Exception;
    void alterPasswordById(UserInfo userInfo);
}
