package com.moon.mybatisplus.samples.enums.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * 通用枚举定义示例方式二：实现 IEnum 接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 9:11
 * @description
 */
@Getter
public enum AgeEnum implements IEnum<Integer> {

    SIXTEEN(16, "十六岁"),
    EIGHTTEEN(18, "十八岁"),
    TWENTY(20, "二十岁");

    private final Integer value;
    private final String desc;

    AgeEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 枚举数据库存储值
     */
    @Override
    public Integer getValue() {
        return this.value;
    }

    /**
     * 重写toString，输出的时显示相应的字段描述
     */
    @Override
    public String toString() {
        return this.desc;
    }

}
