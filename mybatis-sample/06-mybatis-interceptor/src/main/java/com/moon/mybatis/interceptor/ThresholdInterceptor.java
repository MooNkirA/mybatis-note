package com.moon.mybatis.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * 自定义MyBatis插件，该插件当查询操作运行时间超过某个阈值时，记录日志用于定位慢查询
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-06-12 16:55
 * @description
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        // @Signature(type = StatementHandler.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class ThresholdInterceptor implements Interceptor {

    // 查询操作超时阈值
    private long threshold;

    /**
     * 插件对业务进行增强的核心方法
     *
     * @param invocation 拦截到的目标方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 记录开始时间
        long begin = System.currentTimeMillis();
        // 执行sql操作
        Object result = invocation.proceed();
        // 记录结果时间
        long end = System.currentTimeMillis();
        // 执行时间
        long runTime = end - begin;
        if (runTime > this.threshold) {
            // 获取sql的参数
            Object[] args = invocation.getArgs();
            Statement statement = (Statement) args[0];
            MetaObject metaObjectStat = SystemMetaObject.forObject(statement);
            PreparedStatementLogger statementLogger = (PreparedStatementLogger) metaObjectStat.getValue("h");
            PreparedStatement preparedStatement = statementLogger.getPreparedStatement();
            System.out.println("sql语句：“" + preparedStatement.toString() + "”执行时间为：" + runTime + "毫秒，已经超过阈值！");
        }

        return result;
    }

    /**
     * target 是被拦截的对象，此方法的作用是给被拦截的对象生成一个代理对象
     *
     * @param target 是被拦截的对象
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 读取在 plugin 中设置的参数
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        // 读取配置的超时阈值
        this.threshold = Long.valueOf(properties.getProperty("threshold"));
    }
}
