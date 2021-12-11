package com.moon.mybatisplus.samples.optlocker.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.mybatisplus.samples.optlocker.entity.Goods;
import com.moon.mybatisplus.samples.optlocker.mapper.GoodsMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 乐观锁测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 17:15
 * @description
 */
@SpringBootTest
public class OptlockerTest {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 更新版本号
     */
    @Test
    public void testUpdateByIdSucc() {
        // 新增记录
        Goods goods = new Goods();
        goods.setName("篮球");
        goods.setVersion(1);
        goodsMapper.insert(goods);
        // 获取新增后 id
        Long id = goods.getId();

        Goods goodsUpdate = new Goods();
        goodsUpdate.setId(id);
        goodsUpdate.setName("衣服");
        goodsUpdate.setVersion(1);

        // 版本号一致，更新成功
        System.out.println("更新操作数：" + goodsMapper.updateById(goodsUpdate));
        // 更新后 newVersion 会回写到 entity 中
        System.out.println("new version：" + goodsUpdate.getVersion());
    }

    /**
     * 测试更新后，版本号更新写回原对象中。
     */
    @Test
    public void testUpdateByIdSuccVersion() {
        Goods goods = goodsMapper.selectById(1);
        int oldVersion = goods.getVersion();
        System.out.println("old version: " + oldVersion);
        int i = goodsMapper.updateById(goods);
        // 更新后 newVersion 会回写到 entity 中
        System.out.println("version: " + goods.getVersion());
    }

    /**
     * 不带版本号更新数据，更新后版本号不会改变
     */
    @Test
    public void testUpdateByIdSuccWithNoVersion() {
        // 新增记录
        Goods goods = new Goods();
        goods.setName("篮球");
        goods.setVersion(1);
        goodsMapper.insert(goods);
        // 获取新增后 id
        Long id = goods.getId();

        Goods goodsUpdate = new Goods();
        goodsUpdate.setId(id);
        goodsUpdate.setName("衣服");
        goodsUpdate.setVersion(null);
        // Should update success as no version passed in
        System.out.println("更新操作成功数量：" + goodsMapper.updateById(goodsUpdate));
        Goods updated = goodsMapper.selectById(id);
        // Version not changed
        System.out.println("更新后版本号：" + updated.getVersion());
        // Age updated
        System.out.println("商品的名称已经更新为：" + updated.getName());
    }

    /**
     * 测试不正确版本号，更新失败
     */
    @Test
    public void testUpdateByIdFail() {
        // 新增记录
        Goods goods = new Goods();
        goods.setName("篮球");
        goods.setVersion(1);
        goodsMapper.insert(goods);
        // 获取新增后 id
        Long id = goods.getId();

        Goods goodsUpdate = new Goods();
        goodsUpdate.setId(id);
        goodsUpdate.setName("排球");
        // 设置不正确的版本号
        goodsUpdate.setVersion(3);
        // 没有更新到数据
        System.out.println("操作的记录数: " + goodsMapper.updateById(goodsUpdate));
    }

    /**
     * 批量更新带乐观锁
     * <p>
     * update(et,ew) et:必须带上version的值才会触发乐观锁
     */
    @Test
    public void testUpdateByEntitySucc() {
        QueryWrapper<Goods> ew = new QueryWrapper<>();
        ew.eq("version", 1);
        long count = goodsMapper.selectCount(ew);
        System.out.println("版本号是1的记录数：" + count);

        Goods entity = new Goods();
        entity.setName("篮球");
        entity.setVersion(1);

        // updated records should be same
        Assertions.assertEquals(count, goodsMapper.update(entity, null));

        ew = new QueryWrapper<>();
        ew.eq("version", 1);
        // No records found with version=1
        System.out.println("批量更新后版本号是1的记录数：" + goodsMapper.selectCount(ew));

        ew = new QueryWrapper<>();
        ew.eq("version", 2);
        // All records with version=1 should be updated to version=2
        System.out.println("批量更新后版本号是2的记录数：" + goodsMapper.selectCount(ew));
    }

}
