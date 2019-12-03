package com.lvhaifeng.cloud.admin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 角色
 *
 * @author haifeng.lv
 */
@Getter
@Setter
@Table(name = "base_role")
public class BaseRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Id
    private String id;

    /**
     * 角色名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "crt_time")
    private LocalDateTime crtTime;

    /**
     * 创建时间
     */
    @Column(name = "upd_time")
    private LocalDateTime updTime;

}
