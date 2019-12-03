package com.lvhaifeng.cloud.admin.rest;

import com.lvhaifeng.cloud.admin.biz.BaseRoleBiz;
import com.lvhaifeng.cloud.admin.entity.BaseRole;
import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.client.annotation.CheckUserToken;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.msg.ObjectRestResponse;
import com.lvhaifeng.cloud.common.msg.TableResultResponse;
import com.lvhaifeng.cloud.common.rest.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 角色控制器
 *
 * @author haifeng.lv
 * @date 2019-07-27 14:20
 */
@RestController
@RequestMapping("role")
@CheckClientToken
@CheckUserToken
public class BaseRoleController extends BaseController<BaseRoleBiz, BaseRole, String> {
}
