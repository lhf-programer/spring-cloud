package com.lvhaifeng.cloud.admin.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @Description 资源基础类
 * @Author haifeng.lv
 * @Date 2020/1/4 16:19
 */
@Data
public class ResourceInfo {
    private List<MenuInfo> menuInfos;
    private List<ButtonInfo> buttonInfos;
}
