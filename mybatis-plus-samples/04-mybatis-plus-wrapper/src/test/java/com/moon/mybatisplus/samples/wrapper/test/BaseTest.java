package com.moon.mybatisplus.samples.wrapper.test;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试公共逻辑
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-27 16:09
 * @description
 */
@SpringBootTest
public class BaseTest {

    /**
     * 循环输出结果列表
     *
     * @param list
     * @param <T>
     */
    <T> void forEachPrint(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
        System.out.println("--------------------------------");
    }

}
