package com.moon.mybatisplus.samples.enums.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 通用枚举定义示例方式一： 使用 @EnumValue 注解枚举属性
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 9:32
 * @description
 */
@Getter
public enum RoleEnum {

    ADMIN(1L, "管理员"),
    CONSUMER(2L, "用户"),
    PROGRAMMER(3L, "程序猿");

    RoleEnum(Long code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    // 标识此属性是数据库相应要转换的
    @EnumValue
    private final Long code;
    private final String descp;

    /**
     * 重写toString，输出的时显示相应的字段描述
     */
    @Override
    public String toString() {
        return this.descp;
    }

}
