package com.lvhaifeng.cloud.auth.server.modules.oauth.rest;

import com.lvhaifeng.cloud.auth.server.modules.oauth.biz.OauthClientDetailsBiz;
import com.lvhaifeng.cloud.auth.server.modules.oauth.entity.OauthClientDetails;
import com.lvhaifeng.cloud.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauthClientDetails")
public class OauthClientDetailsController extends BaseController<OauthClientDetailsBiz, OauthClientDetails,String> {

}