package com.moon.mybatis.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-11 22:03
 * @description
 */
@Data
public class ConsultConfigArea implements Serializable {

    private static final long serialVersionUID = -3154509548327846606L;
    public String areaCode;
    public String areaName;
    public String state;

}
