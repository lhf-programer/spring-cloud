package com.lvhaifeng.cloud.admin.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lvhaifeng.cloud.admin.entity.Button;
import com.lvhaifeng.cloud.admin.entity.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    /**子菜单*/
    private List<MenuInfo> children;
    /**菜单路径*/
    private String url;
    /**按钮列表*/
    private List<Button> buttonList;
}
