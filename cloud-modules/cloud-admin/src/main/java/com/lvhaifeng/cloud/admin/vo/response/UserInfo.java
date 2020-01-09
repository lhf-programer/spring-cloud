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
    private String name;

    /**
     * 角色名称
     */
    private String roleName;

    private List<MenuInfo> menusList;

    /**
     * 描述
     */
    private String description;
}
