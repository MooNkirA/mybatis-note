package com.moon.mybatisplus.samples.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义删除所有方法，需要继承 AbstractMethod
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 20:19
 * @description
 */
public class TruncateAll extends AbstractMethod {

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
        // 拼接 sql
        String sql = "truncate table " + tableInfo.getTableName();
        // 定义方法名称，与 mapper 接口方法名一致
        String sqlMethod = "truncateAll";
        // 获取 sql数据源
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        // 返回删除的 MappedStatement
        return this.addDeleteMappedStatement(mapperClass, sqlMethod, sqlSource);
    }

}
