package com.lvhaifeng.cloud.api.vo.log;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author haifeng.lv
 * @version 2018-07-01 11:18
 */
@Getter
@Setter
public class LogInfo implements Serializable {
    private String menu;

    private String opt;

    private String uri;


    private LocalDateTime crtTime;

    private String crtUser;

    private String crtName;

    private String crtHost;

    public LogInfo(String menu, String option, String uri, LocalDateTime crtTime, String crtUser, String crtName, String crtHost) {
        this.menu = menu;
        this.opt = option;
        this.uri = uri;
        this.crtTime = crtTime;
        this.crtUser = crtUser;
        this.crtName = crtName;
        this.crtHost = crtHost;
    }
}
