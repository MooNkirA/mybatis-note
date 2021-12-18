package com.moon.mybatisplus.samples.pagination;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义分页类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-28 16:55
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MyPage<T> extends Page<T> {

    private static final long serialVersionUID = -344650460219713271L;

    private String name;

    public MyPage(long current, long size) {
        super(current, size);
    }

}
