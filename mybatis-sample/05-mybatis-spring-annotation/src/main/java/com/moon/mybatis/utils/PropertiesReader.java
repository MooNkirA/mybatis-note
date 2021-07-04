package com.moon.mybatis.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Properties 配置文件解析工具类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-06-12 15:24
 * @description
 */
public class PropertiesReader {

    // 创建Properties对象
    private static final Properties PROPERTY = new Properties();

    // 在静态块中加载资源
    static {
        /*
         * 使用try(){}.. 获取数据源
         * 注意：这是jdk1.7开始支持的特性，如果使用的是低版本 需要提升jdk版本或者更改写法
         */
        try (InputStream is = PropertiesReader.class.getResourceAsStream("/db.properties")) {
            PROPERTY.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字符串类型的值
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return PROPERTY.getProperty(key);
    }

    /**
     * 获取Integer类型的值
     *
     * @param key
     * @return
     */
    public static Integer getInteger(String key) {
        String value = get(key);
        return null == value ? null : Integer.valueOf(value);
    }

    /**
     * 获取Boolean类型的值
     *
     * @param key
     * @return
     */
    public static Boolean getBoolean(String key) {
        String value = get(key);
        return null == value ? null : Boolean.valueOf(value);
    }

    /**
     * 设置一个键值对
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        PROPERTY.setProperty(key, value);
    }

    /**
     * 添加一个键值对
     *
     * @param key
     * @param value
     */
    public static void add(String key, Object value) {
        PROPERTY.put(key, value);
    }

}
