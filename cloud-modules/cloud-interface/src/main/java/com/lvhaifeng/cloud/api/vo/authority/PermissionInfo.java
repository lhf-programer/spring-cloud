package com.lvhaifeng.cloud.api.vo.authority;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description 授权信息
 * @Author haifeng.lv
 * @Date 2019/12/16 18:01
 */
@Getter
@Setter
public class PermissionInfo implements Serializable {
    private String code;
    private String type;
    private String uri;
    private String method;
    private String name;
    private String menu;
}
