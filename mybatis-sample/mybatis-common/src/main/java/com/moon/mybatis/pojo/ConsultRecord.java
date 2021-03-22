package com.moon.mybatis.pojo;

import lombok.Data;

/**
 * consult_record 表映射实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-21 15:11
 * @description
 */
@Data
public class ConsultRecord {

    private Integer id;
    private String psptId;
    private String name;
    private String activeTime;
    private String autograph;
    private String ispass;
    private String docautograph;
    private String fingerprint;
    private String printFlag;
    private String remark;

}
