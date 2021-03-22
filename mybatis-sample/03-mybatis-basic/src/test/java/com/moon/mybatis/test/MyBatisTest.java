package com.moon.mybatis.test;

import com.moon.mybatis.dao.CacheMapper;
import com.moon.mybatis.dao.CommonMapper;
import com.moon.mybatis.dao.ConsultConfigAreaMapper;
import com.moon.mybatis.dao.UserMapper;
import com.moon.mybatis.pojo.ConsultContract;
import com.moon.mybatis.pojo.ConsultIdCardInfo;
import com.moon.mybatis.pojo.ConsultIdCardInfoWithContract;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-13 09:46
 * @description
 */
public class MyBatisTest {

    private final String RESOURCE = "mybatis-config.xml";
    private InputStream inputStream;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        try {
            // 获取SqlSessionFactory
            this.sqlSessionFactory = getSqlSessionFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 单表查询返回多条数据，基础resultMap的使用测试 */
    @Test
    public void testBasicResultMap() {
        CommonMapper mapper = this.getMapper(CommonMapper.class);
        System.out.println(mapper.queryContracts());
    }

    /* 多表联合查询，单条数据中包含单条关联表的数据，resultMap的association使用测试 */
    @Test
    public void testResultMapAssociation() {
        CommonMapper mapper = this.getMapper(CommonMapper.class);
        System.out.println(mapper.queryContractOnebyCardId());
    }

    /* 多表联合查询，单条数据中包含多条关联表的数据，resultMap的association使用测试 */
    @Test
    public void testResultMapCollection() {
        CommonMapper mapper = this.getMapper(CommonMapper.class);
        ConsultIdCardInfoWithContract consultIdCardInfoWithContract = mapper.queryIdcardinfobyCardId("456979432");
        System.out.println(consultIdCardInfoWithContract);
    }

    /* 测试mapper接口使用Map作为方法入参传递 */
    @Test
    public void testMapParam() {
        Map<String, Object> map = new HashMap<>();
        map.put("psptId", 456979432);
        CommonMapper mapper = this.getMapper(CommonMapper.class);
        System.out.println(mapper.queryUserByPsptIdMap(map));
    }

    /* 测试mapper接口使用@Param注解标识方法入参传递 */
    @Test
    public void testAnnotationParam() {
        System.out.println(this.getMapper(CommonMapper.class).queryUserByPsptIdParam("456979432"));
    }

    /* 测试mapper接口使用对象作为方法入参传递 */
    @Test
    public void testObjectParam() {
        ConsultIdCardInfo info = new ConsultIdCardInfo();
        info.setPsptId("456979432");
        System.out.println(this.getMapper(CommonMapper.class).queryUserByPsptIdObj(info));
    }

    /* 使用useGeneratedKeys与keyProperty属性获取数据库生成的主键 */
    @Test
    public void testUseGeneratedKey() {
        ConsultContract consultContract = new ConsultContract();
        consultContract.setActiveTime("2021-3-9");
        consultContract.setContractCode("JK1");
        consultContract.setPsptId("456979433");
        consultContract.setState(1);
        SqlSession sqlSession = this.getSqlSession();
        CommonMapper mapper = this.getMapper(sqlSession, CommonMapper.class);
        System.out.println(mapper.saveContractUseGeneratedKeys(consultContract));
        System.out.println(consultContract.getContractId());
        // 提交事务，关闭连接
        commitAndCloseSqlSession(sqlSession);
    }

    /* 使用<selectKey>标签元素获取数据库生成的主键 */
    @Test
    public void testUseSelectKey() {
        ConsultContract consultContract = new ConsultContract();
        consultContract.setActiveTime("2021-3-10");
        consultContract.setContractCode("MOON");
        consultContract.setPsptId("456979432");
        consultContract.setState(1);
        SqlSession sqlSession = this.getSqlSession();
        CommonMapper mapper = this.getMapper(sqlSession, CommonMapper.class);
        // 这里没有将主键返回？待研究
        System.out.println(mapper.saveContractUseSelectKey(consultContract));
        System.out.println(consultContract.getContractId());
        commitAndCloseSqlSession(sqlSession);
    }

    /* 模拟在使用${}注入参数时sql注入 */
    @Test
    public void testSqlInjection() {
        UserMapper mapper = this.getAutocommitMapper(UserMapper.class);
        // 注意：正常使用了${}来注入参数时，是直接拼接sql的，所以需要自己手动增加单引号
        // System.out.println(mapper.selectByUserName("'MooN2'"));
        // 模拟sql注入
        System.out.println(mapper.selectByUserName("'' union select 1,2,3,4,5 from information_schema.tables;"));
    }

    /*
     * todo: 动态sql示例待补充
     */
    /* trim+if 动态标签测试 */
    @Test
    public void testTrimSql() {
        ConsultConfigAreaMapper mapper = getMapper(ConsultConfigAreaMapper.class);
        // 设置查询条件
        HashMap<String, Object> map = new HashMap<>();
        map.put("state", "1");
        map.put("beginPage", 1);
        map.put("pageSize", 10);
        System.out.println(mapper.queryAreaByCondition(map));
    }

    /* 批量处理新增数据测试 */
    @Test
    public void testBatchInsert() {
        // 获取批处理数据库连接
        SqlSession batchSqlSession = getBatchSqlSession();
        CommonMapper mapper = getMapper(batchSqlSession, CommonMapper.class);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ConsultContract consultContract = new ConsultContract();
            consultContract.setActiveTime("2021-3-10");
            consultContract.setContractCode("ER");
            consultContract.setPsptId("456979432");
            consultContract.setState(1);
            System.out.println(mapper.saveContract(consultContract));
        }
        commitAndCloseSqlSession(batchSqlSession);
        System.out.println(System.currentTimeMillis() - t1); // 耗时：4546
    }

    /* 使用动态sql拼接方式，批量新增数据测试 */
    @Test
    public void testForeachSqlInsert() {
        // 获取默认数据库连接
        SqlSession sqlSession = getSqlSession();
        CommonMapper mapper = getMapper(sqlSession, CommonMapper.class);
        long t1 = System.currentTimeMillis();
        List<ConsultContract> consultContracts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ConsultContract consultContract = new ConsultContract();
            consultContract.setActiveTime("2021-3-10");
            consultContract.setContractCode("ER");
            consultContract.setPsptId("456979432");
            consultContract.setState(1);
            consultContracts.add(consultContract);
        }
        mapper.saveContracts(consultContracts);
        commitAndCloseSqlSession(sqlSession);
        System.out.println(System.currentTimeMillis() - t1); // 耗时：1850
    }

    /* 测试一级缓存：同一个SqlSession同一个命名空间中的同一个查询语句多次执行 */
    @Test
    public void testLevel1Cache() {
        ConsultConfigAreaMapper mapper = getSqlSession().getMapper(ConsultConfigAreaMapper.class);
        System.out.println("=========第一次查询==========");
        System.out.println(mapper.queryAreaByAreaCode());
        System.out.println("=========第二次查询==========");
        System.out.println(mapper.queryAreaByAreaCode());
    }

    /* 测试一级缓存：不同的SqlSession同一个命名空间中的同一个查询语句多次执行 */
    @Test
    public void testLevel1CacheDiffSqlSession() {
        System.out.println("=========第一次查询==========");
        System.out.println(getMapper(ConsultConfigAreaMapper.class).queryAreaByAreaCode());
        System.out.println("=========第二次查询==========");
        System.out.println(getMapper(ConsultConfigAreaMapper.class).queryAreaByAreaCode());
    }

    /* 测试二级缓存：同一个sqlSessionFactory，不同SqlSession同一个命名空间中的同一个查询语句多次执行 */
    @Test
    public void testLevel2CacheSameSqlSessionFactory() {
        // 开启一个SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ConsultConfigAreaMapper mapper = sqlSession.getMapper(ConsultConfigAreaMapper.class);
        System.out.println("=========第一次查询==========");
        System.out.println(mapper.queryAreaByAreaCode());
        System.out.println("=========第二次查询==========");
        System.out.println(mapper.queryAreaByAreaCode());
        // 注意：需要提交事务与关闭sqlSession，才会序列化结果到二级缓存
        sqlSession.commit();
        sqlSession.close();

        // 在同一个 sqlSessionFactory 中开启新的 SqlSession
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        System.out.println("=========新的sqlSession查询第一次查询==========");
        System.out.println(sqlSession1.getMapper(ConsultConfigAreaMapper.class).queryAreaByAreaCode());
        sqlSession1.commit();
        sqlSession1.close();
    }

    /* 测试二级缓存：不同的sqlSessionFactory，不同SqlSession同一个命名空间中的同一个查询语句多次执行 */
    @Test
    public void testLevel2CacheDiffSqlSessionFactory() throws IOException {
        SqlSession sqlSession = getSqlSession();
        ConsultConfigAreaMapper mapper = sqlSession.getMapper(ConsultConfigAreaMapper.class);
        System.out.println("=========第一次查询==========");
        System.out.println(mapper.queryAreaByAreaCode());
        System.out.println("=========第二次查询==========");
        System.out.println(mapper.queryAreaByAreaCode());
        // 注意：需要提交事务与关闭sqlSession，才会序列化结果到二级缓存
        sqlSession.commit();
        sqlSession.close();

        // 获取新的sqlSessionFactory后，再开启一个SqlSession
        SqlSession sqlSession1 = getSqlSessionFactory().openSession();
        System.out.println("=========新的sqlSessionFactory开启的sqlSession查询第一次查询==========");
        System.out.println(sqlSession1.getMapper(ConsultConfigAreaMapper.class).queryAreaByAreaCode());
        sqlSession1.commit();
        sqlSession1.close();
    }

    /*
     *  测试自定义缓存：使用了自定义缓存，自定义结果缓存的规则与存放的位置，
     *              不使用默认的二级缓存存放在sqlSessionFactory中，即不会跨SqlSessionFactory缓存失效的问题
     */
    @Test
    public void testCustomCacheDiffSqlSessionFactory() throws IOException {
        SqlSession sqlSession = getSqlSession();
        CacheMapper mapper = sqlSession.getMapper(CacheMapper.class);
        System.out.println("=========第一次查询==========");
        System.out.println(mapper.selectAllUser());
        System.out.println("=========第二次查询==========");
        System.out.println(mapper.selectAllUser());
        // 注意：一样需要提交事务与关闭sqlSession，才会序列化结果到自定义缓存
        sqlSession.commit();
        sqlSession.close();

        // 获取新的sqlSessionFactory后，再开启一个SqlSession
        SqlSession sqlSession1 = getSqlSessionFactory().openSession();
        System.out.println("=========新的sqlSessionFactory开启的sqlSession查询第一次查询==========");
        System.out.println(sqlSession1.getMapper(CacheMapper.class).selectAllUser());
        sqlSession1.commit();
        sqlSession1.close();
    }

    // *********************************************************************
    //                  抽取的工具方法
    // *********************************************************************

    // 获取 SqlSessionFactory
    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        // 从 XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。
        // MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，可以使类路径或其它位置加载资源文件
        this.inputStream = Resources.getResourceAsStream(RESOURCE);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    // 获取默认的sqlSession
    private SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    // 获取批量处理的sqlSession
    private SqlSession getBatchSqlSession() {
        // 设置session的类型的batch（批处理），并设置不自动提交
        return sqlSessionFactory.openSession(ExecutorType.BATCH, false);
    }

    // 获取自动提交的sqlSession
    private SqlSession getSqlSessionAutocommit() {
        return sqlSessionFactory.openSession(true);
    }

    // 获取 mapper 映射器
    private <T> T getMapper(SqlSession sqlSession, Class<T> clazz) {
        return sqlSession.getMapper(clazz);
    }

    private <T> T getMapper(Class<T> clazz) {
        return getSqlSession().getMapper(clazz);
    }

    private <T> T getBatchMapper(Class<T> clazz) {
        return getBatchSqlSession().getMapper(clazz);
    }

    private <T> T getAutocommitMapper(Class<T> clazz) {
        return getSqlSessionAutocommit().getMapper(clazz);
    }

    // 提交与关闭数据库连接
    private void commitAndCloseSqlSession(SqlSession sqlSession) {
        sqlSession.commit();
        sqlSession.close();
    }

}
