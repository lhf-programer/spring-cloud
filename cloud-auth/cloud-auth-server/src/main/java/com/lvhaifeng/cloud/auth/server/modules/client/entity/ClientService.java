package com.lvhaifeng.cloud.auth.server.modules.client.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 客户端访问授权实体类
 *
 * @author haifeng.lv
 * @date 2019-08-01 15:46
 */
@Table(name = "auth_client_service")
@Getter
@Setter
public class ClientService {
    @Id
    private Integer id;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "description")
    private String description;

    @Column(name = "crt_user_name")
    private String crtUserName;

    @Column(name = "crt_host")
    private String crtHost;

    @Column(name = "crt_time")
    private Date crtTime;

}
