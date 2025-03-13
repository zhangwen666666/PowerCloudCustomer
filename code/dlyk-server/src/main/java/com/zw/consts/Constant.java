package com.zw.consts;

public class Constant {
    public static final String REDIS_TOKEN_KEY = "dlyk:user:login:";

    // jwt过期时间7天
    public static final long EXPIRE_TIME = 7 * 24 * 60 * 60L;

    // jwt过期时间30分钟
    public static final long DEFAULT_EXPIRE_TIME = 30 * 60L;

    // 登录请求的URI
    public static final String LOGIN_URI = "/api/login";

    // redis存储的owner信息key
    public static final String REDIS_OWNER_KEY = "dlyk:user:owner";

    public static final long REDIS_OWNER_KEY_EXPIRE_TIME = 30 * 60L;
}
