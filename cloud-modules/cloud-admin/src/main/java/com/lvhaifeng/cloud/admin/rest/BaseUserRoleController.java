package com.lvhaifeng.cloud.admin.rest;

import com.lvhaifeng.cloud.admin.biz.BaseUserRoleBiz;
import com.lvhaifeng.cloud.admin.entity.BaseUserRole;
import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.client.annotation.CheckUserToken;
import com.lvhaifeng.cloud.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色控制器
 *
 * @author haifeng.lv
 * @date 2019-07-27 14:20
 */
@RestController
@RequestMapping("baseUserRole")
@CheckClientToken
@CheckUserToken
public class BaseUserRoleController extends BaseController<BaseUserRoleBiz, BaseUserRole, String> {

}
