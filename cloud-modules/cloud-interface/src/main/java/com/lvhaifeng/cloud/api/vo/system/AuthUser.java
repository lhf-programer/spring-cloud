package com.lvhaifeng.cloud.api.vo.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户登入鉴权VO
 *
 * @author loki
 * @date 2019-07-29 10:28
 **/
@Getter
@Setter
public class AuthUser implements Serializable {
    private static final long serialVersionUID = -6625521051532170772L;
    private String id;
    /**
     * 用户登入名
     */
    private String username;

    /**
     * 用户登入密码
     */
    private String password;

    /**
     * 用户名称
     */
    private String name;

}
