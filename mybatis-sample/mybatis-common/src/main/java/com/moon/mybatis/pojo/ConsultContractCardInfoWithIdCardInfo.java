package com.moon.mybatis.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * consult_contract 与 consult_idcardinfo 关联查询结果映射实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-13 10:41
 * @description
 */
@Alias("consultContractCardInfoWithIdCardInfo") // 设置mybatis的类型别名
@Data
public class ConsultContractCardInfoWithIdCardInfo implements Serializable {

    private static final long serialVersionUID = -3952761853429605704L;

    private Integer contractId;
    private String psptId;
    private String contractCode;
    private String activeTime;
    private Integer state;

    /* 用于测试多表关联查询中，resultMap结果映射中的 association 属性 */
    private ConsultIdCardInfo info;
}
