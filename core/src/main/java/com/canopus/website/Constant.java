package com.canopus.website;

/**
 * @Author: dai-ych
 * @Date: 2019/1/10 16:41
 * @Description: 常量
 */
public class Constant {

    /**
     * WEB 端, 登录有效时间
     */
    public static final int I_TOKEN_EXPIRE_WEB = 60 * 60 * 12;

    /**
     * 暂时写死的用户名
     */
    public static final String ADMIN = "admin";

    /**
     * 逻辑删除标志位
     */
    public static final int VALIDITY_YES = 1;
    public static final int VALIDITY_NO = 0;


    /**
     * ================================================================================================
     * TABLE: User
     * ================================================================================================
     */
    /**
     * type ：0：全局超级管理员   1：后台管理员
     */
    public static final int USER_TYPE_SUPPER = 0;
    public static final int USER_TYPE_DEFAULT = 1;

    /**
     * status ：0：禁用   1：启用
     */
    public static final int USER_STATUS_BAN = 0;
    public static final int USER_STATUS_DEFAULT = 1;
}
