package com.zw.util;

import org.springframework.util.ObjectUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CacheUtil {
    /**
     * 带有缓存的查询工具方法
     * @param cacheSelector 从缓存中查询数据
     * @param dbSelector  从数据库中查询数据
     * @param cacheSave 缓存中没有查询到数据，从数据库中查询到数据，需要把数据放入缓存中
     * @return 查询到的数据
     * @param <T> 泛型
     */
    public static <T> T getCacheData(Supplier<T> cacheSelector, Supplier<T> dbSelector, Consumer<T> cacheSave){
        // 1. 从缓存中查询数据
        T data = cacheSelector.get();
        if(ObjectUtils.isEmpty(data)){
            // 缓存中没查到, 从数据库中查询
            data = dbSelector.get();
            if(!ObjectUtils.isEmpty(data)){
                // 数据库中查询到了数据，需要把数据放入缓存中
                cacheSave.accept(data);
            }
        }
        return data;
    }
}
