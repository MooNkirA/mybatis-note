package com.moon.mybatisplus.samples.id.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_order")
@Data
public class Order {

    /**
     * 主键类型设置为分配ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private BigInteger amt;

}
