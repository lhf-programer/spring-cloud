package com.lvhaifeng.cloud.admin.vo.response;

import com.lvhaifeng.cloud.admin.entity.Button;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/1/6 18:16
 */
@Data
public class MenuInfo {
    /**id*/
    private String id;
    /**菜单名称*/
    private String name;
    /**父菜单id*/
    private String parentId;
    /**父菜单*/
    private MenuInfo parent;
    /**描述*/
    private String description;
    /**子菜单*/
    private List<MenuInfo> children;
    /**角色 id*/
    private String roleId;
    /**菜单路径*/
    private String url;
    /**按钮列表*/
    private List<Button> buttonList;
}
