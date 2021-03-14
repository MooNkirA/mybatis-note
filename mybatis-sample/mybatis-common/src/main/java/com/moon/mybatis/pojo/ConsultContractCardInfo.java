package com.moon.mybatis.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * consult_contract 表映射实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-13 09:28
 * @description
 */
@Alias("consultContractCardInfo") // 设置mybatis的类型别名
@Data
public class ConsultContractCardInfo implements Serializable {
    private static final long serialVersionUID = 5898926697727427877L;

    private Integer contractId;
    private String psptId;
    private String contractCode;
    private String activeTime;
    private Integer state;

}
