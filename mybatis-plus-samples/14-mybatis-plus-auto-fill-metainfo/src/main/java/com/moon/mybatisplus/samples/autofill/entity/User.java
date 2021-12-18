package com.moon.mybatisplus.samples.autofill.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * user 表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 15:13
 * @description
 */
@TableName("user")
@Data
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    /*
     * 自动填充的字段必须声明 `@TableField` 注解，属性`fill`选择对应自动填充的策略
     *   FieldFill 是枚举，取值如下：
     *       - DEFAULT：默认不处理
     *       - INSERT：插入时填充字段
     *       - UPDATE：更新时填充字段
     *       - INSERT_UPDATE：插入和更新时填充字段
     */
    @TableField(fill = FieldFill.INSERT) // 插入数据时进行填充
    private Long roleId;

}
