package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.ConsultConfigArea;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 使用注解映射sql，不依赖xml
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-12 15:47
 * @description
 */
public interface Anno_CommonMapper {

    @Select("select `areaCode`,`areaName`,`state` from consult_configarea")
    List<ConsultConfigArea> queryAreaByAreaCode(Map param);

}
