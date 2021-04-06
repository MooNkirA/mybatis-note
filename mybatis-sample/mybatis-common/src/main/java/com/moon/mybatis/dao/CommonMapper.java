package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.ConsultContract;
import com.moon.mybatis.pojo.ConsultContractCardInfo;
import com.moon.mybatis.pojo.ConsultContractCardInfoWithIdCardInfo;
import com.moon.mybatis.pojo.ConsultIdCardInfo;
import com.moon.mybatis.pojo.ConsultIdCardInfoWithContract;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper映射器接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-11 22:02
 * @description
 */
public interface CommonMapper {

    /* 返回多条数据的resultMap测试 */
    List<ConsultContract> queryContracts();

    /* 多表联合查询，单条数据中包含单条关联表的数据，resultMap的association使用测试 */
    ConsultContractCardInfoWithIdCardInfo queryContractOnebyCardId();

    /* 多表联合查询，单条数据中包含多条关联表的数据，resultMap的collection使用测试 */
    ConsultIdCardInfoWithContract queryIdcardinfobyCardId(@Param("psptId") String psptId);

    /* 单表查询，resultMap的子标签collection的select属性测试嵌套查询 */
    List<ConsultContractCardInfo> queryAllConsultContract();

    /* 使用Map作为方法入参传递 */
    List<Map<String, Object>> queryUserByPsptIdMap(Map<String, Object> param);

    /* 使用@Param注解标识方法入参传递 */
    List<Map<String, Object>> queryUserByPsptIdParam(@Param("psptId") String psptId);

    /* 使用对象作为方法入参传递 */
    List<Map<String, Object>> queryUserByPsptIdObj(ConsultIdCardInfo info);

    /* 使用useGeneratedKeys与keyProperty属性获取数据库生成的主键 */
    int saveContractUseGeneratedKeys(@Param("contract") ConsultContract contract);

    /* 使用<selectKey>标签元素获取数据库生成的主键 */
    int saveContractUseSelectKey(@Param("contract") ConsultContract contract);

    /* 普通的新增语句，用于测试批处理方式新增 */
    int saveContract(ConsultContract contract);

    /* 动态sql <foreach>标签拼接sql批量新增 */
    int saveContracts(List<ConsultContract> contractList);
}
