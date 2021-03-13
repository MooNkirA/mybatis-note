package com.moon.mybatis.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-12 16:14
 * @description
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -6588958936403857195L;

    private Integer id; //int(11) NOT NULL AUTO_INCREMENT
    private String username; //varchar(32) NOT NULL COMMENT '用户名称'
    private Date birthday; //date DEFAULT NULL COMMENT '生日'
    private String sex; //char(1) DEFAULT NULL COMMENT '性别'
    private String address; //varchar(256) DEFAULT NULL COMMENT '地址'

}
