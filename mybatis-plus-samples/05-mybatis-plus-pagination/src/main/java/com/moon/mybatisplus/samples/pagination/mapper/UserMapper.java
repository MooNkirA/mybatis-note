package com.moon.mybatisplus.samples.pagination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moon.mybatisplus.samples.pagination.MyPage;
import com.moon.mybatisplus.samples.pagination.entity.User;

import java.util.List;

/**
 * user 表持久接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-28 9:40
 * @description
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义 mapper 方法分页。
     * 如果返回类型是 IPage 则入参的 IPage 不能为null,因为 返回的IPage == 入参的IPage
     *
     * @param page
     * @return
     */
    IPage<User> selectReturnIPage(IPage<User> page, String name);

    /**
     * 自定义 mapper 方法分页。
     * 如果返回类型是 List 则入参的 IPage 可以为 null(为 null 则不分页),但需要你手动 入参的IPage.setRecords(返回的 List);
     *
     * @param page
     * @return
     */
    List<User> selectByIPage(IPage<User> page);

    /**
     * 自定义分页对象
     *
     * @param myPage
     * @return
     */
    MyPage<User> selectByMyPage(MyPage<User> myPage);

}
