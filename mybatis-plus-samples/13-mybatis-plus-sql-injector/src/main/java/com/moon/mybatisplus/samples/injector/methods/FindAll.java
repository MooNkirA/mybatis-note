package com.moon.mybatisplus.samples.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义查询所有方法，需要继承 AbstractMethod
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 20:19
 * @description
 */
public class FindAll extends AbstractMethod {

    /**
     * 注入自定义 MappedStatement
     *
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        /* 组装待执行的 SQL ，动态 SQL 参考类 SqlMethod */
        // 1. 获取表名
        String tableName = tableInfo.getTableName();
        // 2. 拼接 sql
        String sql = "select * from " + tableName;
        // 3. 定义方法名称，与 mapper 接口方法名一致
        String sqlMethod = "findAll";
        // 4. 获取 sql数据源
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);

        /*
         * 5. 返回查询的 MappedStatement
         * 方法参数：
         * Class<?> mapperClass : mapper 接口
         * String id : 方法名
         * SqlSource sqlSource : sql数据源
         * TableInfo table : 表信息
         */
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod, sqlSource, tableInfo);
    }

}
