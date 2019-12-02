package com.lvhaifeng.cloud.auth.server.modules.oauth.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Table(name = "oauth_client_details")
@Data
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Id
    private String clientId;

    //
    @Column(name = "resource_ids")
    private String resourceIds;

    //
    @Column(name = "client_secret")
    private String clientSecret;

    //
    @Column(name = "scope")
    private String scope;

    //
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    //
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

    //
    @Column(name = "authorities")
    private String authorities;

    //
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    //
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    //
    @Column(name = "additional_information")
    private String additionalInformation;

    //
    @Column(name = "autoapprove")
    private String autoapprove;

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
    private String description;

    @Column(name = "is_deleted")
    private String isDeleted;

    @Column(name = "is_disabled")
    private String isDisabled;
}
