package com.moon.mybatisplus.samples.logic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.mybatisplus.samples.logic.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * goods 表持久接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-11 23:15
 * @description
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}
