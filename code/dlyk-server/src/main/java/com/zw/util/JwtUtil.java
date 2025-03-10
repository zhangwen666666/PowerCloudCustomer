package com.zw.util;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.zw.entity.TUser;

import java.util.Map;

public class JwtUtil {
    public static final String key = "fhjk867asljf?/1=+23f/s8;HA!SfsdG";

    /**
     * 生成JWT
     *
     * @param user
     * @return
     */
    public static String createJWT(TUser user) {
        return JWTUtil.createToken(Map.of("user", user), key.getBytes());
    }

    /**
     * 验证JWT
     * @param token
     * @return
     */
    public static Boolean verifyToken(String token) {
        return JWTUtil.verify(token, key.getBytes());
    }

    /**
     * 解析JWT
     * @param token
     * @return
     */
    public static TUser parseToken(String token){
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return payloads.get("user", TUser.class);
    }
}
