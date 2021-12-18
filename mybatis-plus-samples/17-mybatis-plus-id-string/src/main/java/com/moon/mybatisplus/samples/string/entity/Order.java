package com.moon.mybatisplus.samples.string.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigInteger;

/**
 * order 表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 10:53
 * @description
 */
@Data
public class Order {

    /**
     * 字符串自增 ID
     */
    @TableId(type = IdType.AUTO)
    private String id;
    private BigInteger amt;

}
