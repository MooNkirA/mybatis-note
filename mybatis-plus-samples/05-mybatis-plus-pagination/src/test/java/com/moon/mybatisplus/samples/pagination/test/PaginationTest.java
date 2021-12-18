package com.moon.mybatisplus.samples.pagination.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moon.mybatisplus.samples.pagination.MyPage;
import com.moon.mybatisplus.samples.pagination.entity.User;
import com.moon.mybatisplus.samples.pagination.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * MP 分页插件测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-28 9:44
 * @description
 */
@SpringBootTest
public class PaginationTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * BaseMapper 自带分页测试
     */
    @Test
    public void testBaseMapperPagination() {
        // 创建分页对象
        Page<User> page = new Page<>(1, 2);
        printPageInfo(userMapper.selectPage(page, Wrappers.<User>lambdaQuery().gt(User::getAge, 19)));
    }

    /**
     * 测试分页只查询当前页的记录，不查询总记录数
     */
    @Test
    public void testPaginationNotSearchCount() {
        // 使用三参数的构造器创建Page对象
        // 第三个参数searchCount：传true则查询总记录数；传false则不查询总记录数（即不进行count查询）
        Page<User> page = new Page<>(1, 2, false);
        // 因为没有进行count查询，getTotal方法返回值为0
        printPageInfo(userMapper.selectPage(page, Wrappers.<User>lambdaQuery().gt(User::getAge, 19)));
    }

    /**
     * 自定义的 mapper 方法使用分页，入参为IPage，返回IPage
     */
    @Test
    public void testSelectReturnIPage() {
        IPage<User> page = new Page<>(1, 2);
        // 如果返回类型是 IPage 则入参的 IPage 不能为null,因为 返回的IPage == 入参的IPage
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        IPage<User> users = userMapper.selectReturnIPage(page, "%a%");
        printPageInfo(users);
    }

    /**
     * 自定义的 mapper 方法使用分页，入参为IPage，返回List
     */
    @Test
    public void testSelectByIPage() {
        IPage<User> page = new Page<User>(1, 2) {
            private static final long serialVersionUID = 6954054104022283002L;
            private String name = "%a%";

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        };

        List<User> users = userMapper.selectByIPage(page);
        users.forEach(System.out::println);
        System.out.println("list.size=" + users.size());
        System.out.println("page.total=" + page.getTotal());
    }

    /**
     * 自定义的 mapper 方法使用分页，入参与返回均使用自定义分页对象
     */
    @Test
    public void testSelectByMyPage() {
        MyPage<User> page = new MyPage<>(1, 2);
        page.setName("%");

        MyPage<User> users = userMapper.selectByMyPage(page);
        users.getRecords().forEach(System.out::println);
        System.out.println("page.total=" + page.getTotal());
    }

    private <T> void printPageInfo(IPage<T> page) {
        if (page == null) {
            return;
        }
        System.out.println("总条数 -----> " + page.getTotal()); // 因为没有进行count查询，getTotal值为0
        System.out.println("当前页数 -----> " + page.getCurrent());
        System.out.println("当前每页显示数 -----> " + page.getSize());
        page.getRecords().forEach(System.out::println);
    }

}
