package com.moon.mybatisplus.samples.logic;

import com.moon.mybatisplus.samples.logic.mapper.GoodsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 逻辑删除测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-11 23:18
 * @description
 */
@SpringBootTest
public class LogicDeleteTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void testLogicDelteById(){
        int i = goodsMapper.deleteById(1L);
        System.out.println(i);
    }

}
