package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.User;
import com.lvhaifeng.cloud.admin.mapper.UserMapper;
import com.lvhaifeng.cloud.admin.service.IUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2019-12-16 16:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
