package com.lvhaifeng.cloud.auth.server.modules.client.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 路由实体类
 *
 * @author haifeng.lv
 * @date 2019-08-01 15:46
 */
@Table(name = "gateway_route")
@Getter
@Setter
public class GatewayRoute implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Id
    private String id;

    //映射路劲
    @Column(name = "path")
    private String path;

    //映射服务
    @Column(name = "service_id")
    private String serviceId;

    //映射外连接
    @Column(name = "url")
    private String url;

    //是否重试
    @Column(name = "retryable")
    private Boolean retryable;

    //是否启用
    @Column(name = "enabled")
    private Boolean enabled;

    //是否忽略前缀
    @Column(name = "strip_prefix")
    private Boolean stripPrefix;

    //创建人
    @Column(name = "crt_user_name")
    private String crtUserName;

    //创建人ID
    @Column(name = "crt_user_id")
    private String crtUserId;

    //创建时间
    @Column(name = "crt_time")
    private Date crtTime;

    //最后更新人
    @Column(name = "upd_user_name")
    private String updUserName;

    //最后更新人ID
    @Column(name = "upd_user_id")
    private String updUserId;

    //最后更新时间
    @Column(name = "upd_time")
    private Date updTime;
}
