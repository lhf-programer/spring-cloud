package com.lvhaifeng.cloud.admin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author haifeng.lv
 */
@Getter
@Setter
@Table(name = "base_user")
public class BaseUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据库主键
     */
    @Id
    private String id;
    /**
     * 登入名
     */
    @Column(name = "login_name")
    private String loginName;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    /**
     * 真实名称
     */
    @Column(name = "real_name")
    private String realName;
    /**
     * 创建时间
     */
    @Column(name = "crt_time")
    private LocalDateTime crtTime;
    /**
     * 更新时间
     */
    @Column(name = "upd_time")
    private LocalDateTime updTime;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    /**
     * 简介
     */
    @Column(name = "introduce")
    private String introduce;
    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;
    /**
     * 上一次登入时间
     */
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;
    /**
     * 性别
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

}
