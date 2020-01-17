package com.lvhaifeng.cloud.common.error;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/1/6 10:33
 */
public class ErrCodeBaseConstant {
    /**
     * 通用，参数错误；只用错误码、不用错误消息
     */
    public static final ErrorCode COMMON_PARAM_ERR = new ErrorCode("10000", "参数错误");
    /**
     * 没有查询到资源
     */
    public static final ErrorCode NO_RESOURCE_ERR = new ErrorCode("20001", "没有查询到该资源");
    /**
     * 没有查询到角色
     */
    public static final ErrorCode NO_ROLE_ERR = new ErrorCode("20002", "没有查询到角色");
    /**
     * 该用户名已被使用
     */
    public static final ErrorCode REPEAT_USER_ERR = new ErrorCode("20003", "该用户名已被使用");
    /**
     * 密码不得为空
     */
    public static final ErrorCode NO_PASSWORD_ERR = new ErrorCode("20004", "密码不得为空");
    /**
     * admin 用户不得删除
     */
    public static final ErrorCode NO_ADMIN_PERMIT_ERR = new ErrorCode("20005", "admin 用户不得删除");
    /**
     * 没有查询到用户
     */
    public static final ErrorCode NO_USER_ERR = new ErrorCode("20006", "没有查询到用户");
    /**
     * 该角色下有用户，无法删除
     */
    public static final ErrorCode NO_ROLE_PERMIT_ERR = new ErrorCode("20007", "该角色下有用户，无法删除");
    /**
     * 该菜单下有子菜单，无法删除
     */
    public static final ErrorCode NO_CHILDREN_MENU_PERMIT_ERR = new ErrorCode("20008", "该菜单下有子菜单，无法删除");
}
