package com.moon.mybatis.test;

import com.moon.mybatis.dao.CommonMapper;
import com.moon.mybatis.dao.UserMapper;
import com.moon.mybatis.pojo.ConsultContract;
import com.moon.mybatis.pojo.ConsultIdCardInfo;
import com.moon.mybatis.pojo.ConsultIdCardInfoWithContract;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-13 09:46
 * @description
 */
public class MyBatisTest {

    private InputStream inputStream;
    private SqlSession sqlSession;
    private CommonMapper commonMapper;

    @Before
    public void init() {
        try {
            // 从 XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。
            String resource = "mybatis-config.xml";
            // MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，可以使类路径或其它位置加载资源文件
            inputStream = Resources.getResourceAsStream(resource);
            // 获取sqlSession的
            this.sqlSession = this.getSqlSession();
            this.commonMapper = sqlSession.getMapper(CommonMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 抽取获取sqlSession的逻辑
    private SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    // 抽取提交与关闭数据库连接逻辑
    private void commitAndCloseSqlSession() {
        sqlSession.commit();
        sqlSession.close();
    }

    /* 多表联合查询，单条数据中包含单条关联表的数据，resultMap的association使用测试 */
    @Test
    public void testResultMapAssociation() {
        System.out.println(this.commonMapper.queryContractOnebyCardId());
    }

    /* 多表联合查询，单条数据中包含多条关联表的数据，resultMap的association使用测试 */
    @Test
    public void testResultMapCollection() {
        ConsultIdCardInfoWithContract consultIdCardInfoWithContract = this.commonMapper.queryIdcardinfobyCardId("456979432");
        System.out.println(consultIdCardInfoWithContract);
    }

    /* 测试mapper接口使用Map作为方法入参传递 */
    @Test
    public void testMapParam() {
        Map<String, Object> map = new HashMap<>();
        map.put("psptId", 456979432);
        System.out.println(this.commonMapper.queryUserByPsptIdMap(map));
    }

    /* 测试mapper接口使用@Param注解标识方法入参传递 */
    @Test
    public void testAnnotationParam() {
        System.out.println(this.commonMapper.queryUserByPsptIdParam("456979432"));
    }

    /* 测试mapper接口使用对象作为方法入参传递 */
    @Test
    public void testObjectParam() {
        ConsultIdCardInfo info = new ConsultIdCardInfo();
        info.setPsptId("456979432");
        System.out.println(this.commonMapper.queryUserByPsptIdObj(info));
    }

    /* 使用useGeneratedKeys与keyProperty属性获取数据库生成的主键 */
    @Test
    public void testUseGeneratedKey() {
        ConsultContract consultContract = new ConsultContract();
        consultContract.setActiveTime("2021-3-9");
        consultContract.setContractCode("JK1");
        consultContract.setPsptId("456979433");
        consultContract.setState(1);
        System.out.println(this.commonMapper.saveContractUseGeneratedKeys(consultContract));
        System.out.println(consultContract.getContractId());
        // 提交事务，关闭连接
        commitAndCloseSqlSession();
    }

    /* 使用<selectKey>标签元素获取数据库生成的主键 */
    @Test
    public void testUseSelectKey() {
        ConsultContract consultContract = new ConsultContract();
        consultContract.setActiveTime("2021-3-10");
        consultContract.setContractCode("MOON");
        consultContract.setPsptId("456979432");
        consultContract.setState(1);
        // 这里没有将主键返回？待研究
        System.out.println(this.commonMapper.saveContractUseSelectKey(consultContract));
        System.out.println(consultContract.getContractId());
        commitAndCloseSqlSession();
    }

    /* 模拟在使用${}注入参数时sql注入 */
    @Test
    public void testSqlInjection() {
        UserMapper mapper = this.sqlSession.getMapper(UserMapper.class);
        // 注意：正常使用了${}来注入参数时，是直接拼接sql的，所以需要自己手动增加单引号
        // System.out.println(mapper.selectByUserName("'MooN2'"));
        // 模拟sql注入
        System.out.println(mapper.selectByUserName("'' union select 1,2,3,4,5 from information_schema.tables;"));
        commitAndCloseSqlSession();
    }

    /*
     * todo: 动态sql示例待补充
     */
}
