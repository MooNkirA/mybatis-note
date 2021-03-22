package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.ConsultRecord;

import java.util.List;
import java.util.Map;

/**
 * ConsultRecord 接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-21 15:14
 * @description
 */
public interface ConsultRecordMapper {

    List<ConsultRecord> queryRecords(Map param);

}
