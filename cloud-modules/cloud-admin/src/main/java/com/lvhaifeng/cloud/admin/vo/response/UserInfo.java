package com.lvhaifeng.cloud.admin.vo.response;

import com.lvhaifeng.cloud.admin.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @Description 用户基本信息
 * @Author haifeng.lv
 * @Date 2020/1/6 15:58
 */
@Data
public class UserInfo {
    private String id;
    /**
     * 名称
     */
    private String username;
    /**
     * 名称
     */
    private String realname;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 菜单列表
     */
    private List<MenuInfo> menusList;
    /**
     * 角色 id
     */
    private List<String> roleId;
    /**
     * 密码
     */
    private String password;
    /**
     * 描述
     */
    private String description;
}
