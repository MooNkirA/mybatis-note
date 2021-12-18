package com.moon.mybatisplus.samples.logic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * goods 商品表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-11 23:15
 * @description
 */
@Data
@TableName("goods")
public class Goods {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer version;
    /**
     * mp 实现逻辑删除，需要实体类相应的字段上加上 @TableLogic 注解
     */
    @TableLogic
    private Integer deleted;

}
