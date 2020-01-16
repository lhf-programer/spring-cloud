package com.lvhaifeng.cloud.admin.vo.response;

import lombok.Data;

/**
 * @Description 按钮基础信息
 * @Author haifeng.lv
 * @Date 2020/1/15 16:58
 */
@Data
public class ButtonInfo {
    private String id;
    /** 名称 **/
    private String name;
    /**描述*/
    private String description;
    /**按钮路径*/
    private String url;
    /**所属菜单id*/
    private String menuId;
    /** 是否选中 **/
    private boolean isCheck;
}
