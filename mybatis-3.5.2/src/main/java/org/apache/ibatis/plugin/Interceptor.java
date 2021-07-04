/**
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.plugin;

import java.util.Properties;

/**
 * @author Clinton Begin
 */
public interface Interceptor {

  /**
   * 拦截器类必须实现该方法。
   * 执行拦截逻辑，是插件对业务进行增强的核心方法
   *
   * @param invocation 拦截到的目标方法
   * @return
   * @throws Throwable
   */
  Object intercept(Invocation invocation) throws Throwable;

  /**
   * 拦截器类可以选择实现该方法。该方法中可以输出一个对象来替换输入参数传入的目标对象。
   * 即给被拦截的对象生成一个代理对象
   *
   * @param target 是被拦截的对象
   * @return
   */
  default Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  /**
   * 拦截器类可以选择实现该方法。该方法用来为拦截器设置属性。
   * 读取在plugin中设置的参数
   *
   * @param properties
   */
  default void setProperties(Properties properties) {
    // NOP
  }

}
