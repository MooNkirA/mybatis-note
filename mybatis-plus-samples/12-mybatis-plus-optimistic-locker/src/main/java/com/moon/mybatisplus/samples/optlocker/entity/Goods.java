package com.moon.mybatisplus.samples.optlocker.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * goods 商品表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 17:15
 * @description
 */
@Data
@TableName("goods")
public class Goods {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    /*
     * 在实体类的相应控制版本字段上加上@Version注解
     * 说明：
     *  - 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     *  - 整数类型下 newVersion = oldVersion + 1
     *  - newVersion 会回写到 entity 中
     *  - 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     *  - 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     */
    @Version
    private Integer version;

}
