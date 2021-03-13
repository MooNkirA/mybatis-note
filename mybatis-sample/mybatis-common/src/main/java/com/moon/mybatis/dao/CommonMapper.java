package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.ConsultConfigArea;

import java.util.List;
import java.util.Map;

/**
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-11 22:02
 * @description
 */
public interface CommonMapper {

    List<ConsultConfigArea> queryAreaByAreaCode(Map param);

}
