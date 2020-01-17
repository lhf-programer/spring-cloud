package com.lvhaifeng.cloud.admin.vo.request;

import lombok.Data;

import java.util.List;

/**
 * @Description 添加角色资源
 * @Author haifeng.lv
 * @Date 2020/1/16 10:28
 */
@Data
public class AddRoleResource {
    /**
     * 用户角色 id
     */
    private String id;

    /**
     * 角色菜单
     */
    private List<String> menuIds;

    /**
     * 角色按钮
     */
    private List<String> buttonIds;
}
