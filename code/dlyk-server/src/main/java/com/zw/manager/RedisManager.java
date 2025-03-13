package com.zw.manager;

import com.zw.consts.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisManager {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 从redis中查询list类型的数据
     * @param key redis中的key
     * @return 取出的数据
     */
    public Object getValue(String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 向redis中添加list类型的数据
     * @param key
     * @param value list集合，存放了负责人的信息
     */
    public <T> void setValue(String key, List<T> value){
        Object[] array = value.toArray();
        redisTemplate.opsForList().rightPushAll(key, array);
        redisTemplate.expire(key, Constant.REDIS_OWNER_KEY_EXPIRE_TIME, TimeUnit.SECONDS);
    }
}
