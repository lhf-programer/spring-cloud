package com.lvhaifeng.cloud.admin.vo.request;

import lombok.Data;

/**
 * @Description 菜单基础类
 * @Author haifeng.lv
 * @Date 2020/1/4 16:19
 */
@Data
public class MenuInfo {
    private String id;
    /**描述*/
    private String description;
    /**菜单名称*/
    private String name;
    /**父菜单id*/
    private String parent;
    /**菜单路径*/
    private String url;
    /**角色id*/
    private String roleId;
}
