package com.lvhaifeng.cloud.admin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户角色实体类
 *
 * @author haifeng.lv
 */
@Getter
@Setter
@Table(name = "base_user_role")
public class BaseUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private String roleId;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 创建时间
     */
    @Column(name = "crt_time")
    private LocalDateTime crtTime;
    /**
     * 修改时间
     */
    @Column(name = "upd_time")
    private LocalDateTime updTime;
}
