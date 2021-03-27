package com.moon.mybatis.cache;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.ibatis.cache.Cache;

import java.time.Duration;

/**
 * 自定义缓存，需要实现 mybatis 的 Cache 接口。
 * 此示例借助了第三方缓存框架 caffeine 来实现功能
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-15 22:41
 * @description
 */
public class CustomCache implements Cache {

    private final String id;

    static com.github.benmanes.caffeine.cache.Cache<String, String> cache = null;

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(100L))
                .maximumSize(10000L)
                .initialCapacity(10)
                .build();
    }

    public CustomCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(JSONObject.toJSONString(key), JSONObject.toJSONString(value));
    }

    @Override
    public Object getObject(Object key) {
        return JSONObject.parse(cache.getIfPresent(JSONObject.toJSONString(key)));
    }

    @Override
    public Object removeObject(Object key) {
        cache.invalidate(key);
        return null;
    }

    @Override
    public void clear() {
        cache.cleanUp();
    }

    @Override
    public int getSize() {
        return Integer.parseInt(cache.estimatedSize() + "");
    }

}
