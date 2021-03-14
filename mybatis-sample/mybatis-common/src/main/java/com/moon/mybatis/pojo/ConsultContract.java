package com.moon.mybatis.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * consult_contract 表映射映射实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-13 16:30
 * @description
 */
@Alias("consultContract")
@Data
public class ConsultContract implements Serializable {

    private static final long serialVersionUID = 209994719586927947L;

    private Integer contractId;
    private String psptId;
    private String contractCode;
    private String activeTime;
    private Integer state;

}
