package com.moon.mybatisplus.samples.injector.base;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.moon.mybatisplus.samples.injector.methods.FindAll;
import com.moon.mybatisplus.samples.injector.methods.TruncateAll;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义 SQL 注入器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 20:13
 * @description
 */
// 将自定义 SQL 注入器加入 Spring 容器中
@Component
/*
 * 如果自定义Injector是继承 AbstractSqlInjector，原有的 BaseMapper 中的方法将失效。
 * 所以选择继承默认实现 DefaultSqlInjector，在其基础上进行扩展
 */
public class MySqlInjector extends DefaultSqlInjector {

    /**
     * 在此方法中加入扩展的自定义方法
     *
     * @param mapperClass
     * @param tableInfo
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        // 1. 获取原有 BaseMapper 中的所有方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);

        // 2. 增加自定义方法
        methodList.add(new FindAll());
        methodList.add(new TruncateAll());

        return methodList;
    }
}
