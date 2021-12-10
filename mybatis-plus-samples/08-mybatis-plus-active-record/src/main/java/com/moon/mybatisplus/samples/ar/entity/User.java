package com.moon.mybatisplus.samples.ar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 9:37
 * @description
 */
/*
 *  MybatisPlus 要实现 ActiveRecord 的功能，
 *  需要继承 com.baomidou.mybatisplus.extension.activerecord.Model
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 5734014396574993178L;

    // 配置主键生成策略
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;

    /**
     * 主键值。重写此方法
     * AR 模式这个必须有，否则 xxById 的方法都将失效！
     * 另外 UserMapper 也必须 AR 依赖该层注入，有可无 XML
     */
    @Override
    public Serializable pkVal() {
        return id;
    }

}
