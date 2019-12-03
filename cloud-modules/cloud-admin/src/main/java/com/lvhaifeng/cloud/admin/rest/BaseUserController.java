package com.lvhaifeng.cloud.admin.rest;


import com.lvhaifeng.cloud.admin.biz.BaseUserBiz;
import com.lvhaifeng.cloud.admin.entity.BaseUser;
import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.client.annotation.CheckUserToken;
import com.lvhaifeng.cloud.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author haifeng.lv
 * @date 2019-07-27 14:20
 */
@RestController
@RequestMapping("user")
@CheckClientToken
@CheckUserToken
public class BaseUserController extends BaseController<BaseUserBiz, BaseUser, String> {
}
