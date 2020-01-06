package com.lvhaifeng.cloud.admin.constant;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/1/4 16:54
 */
public enum ResourceTypeEnum {

    /**
     * 0 菜单
     * 1 按钮
     */
    MENU(0), BUTTON(1);

    private int type;

    ResourceTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
