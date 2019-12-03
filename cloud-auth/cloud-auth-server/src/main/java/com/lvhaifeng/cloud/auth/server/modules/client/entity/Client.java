package com.lvhaifeng.cloud.auth.server.modules.client.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 客户端实体类
 * @author haifeng.lv
 * @date 2019-08-01 15:47
 */
@Table(name = "auth_client")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 客户端 id
     */
    @Column(name = "code")
    private String code;

    @Column(name = "secret")
    private String secret;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "crt_user_name")
    private String crtUserName;

    @Column(name = "crt_host")
    private String crtHost;

    @Column(name = "upd_host")
    private String updHost;

    @Column(name = "crt_time")
    private Date crtTime;

    @Column(name = "upd_time")
    private Date updTime;
}
