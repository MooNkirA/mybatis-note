package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.ConsultConfigArea;

import java.util.List;
import java.util.Map;

/**
 * ConsultConfigArea 接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-21 15:27
 * @description
 */
public interface ConsultConfigAreaMapper {

    /* 返回多条数据的resultMap测试 */
    List<ConsultConfigArea> queryAreaByAreaCode();

    List<ConsultConfigArea> queryAreaByCondition(Map<String, Object> param);

}
