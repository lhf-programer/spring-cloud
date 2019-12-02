package com.lvhaifeng.cloud.api.vo.authority;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


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
