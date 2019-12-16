package com.lvhaifeng.cloud.api.vo.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description 用户登入鉴权VO
 * @Author haifeng.lv
 * @Date 2019/12/16 18:01
 */
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
