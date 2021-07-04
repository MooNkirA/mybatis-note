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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.reflection.ExceptionUtil;

/**
 * @author Clinton Begin
 */
public class Plugin implements InvocationHandler {
  // 封装的真正提供服务的对象（即被代理对象）
  private final Object target;
  // 自定义的拦截器
  private final Interceptor interceptor;
  // 解析@Intercepts注解得到的signature信息，包含拦截器要拦截的所有类，以及类中的方法
  private final Map<Class<?>, Set<Method>> signatureMap;

  private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
    this.target = target;
    this.interceptor = interceptor;
    this.signatureMap = signatureMap;
  }

  /**
   * 用于帮助Interceptor生成动态代理，会根据拦截器的配置来生成一个对象用来替换被代理对象。
   * 因此，如果一个目标类需要被某个拦截器拦截的话，那么这个类的对象已经在 warp方法中被替换成了代理对象，即 Plugin对象。
   * @param target 被代理对象
   * @param interceptor 拦截器
   * @return 用来替换被代理对象的对象
   */
  public static Object wrap(Object target, Interceptor interceptor) {
	  // 解析拦截器Interceptor上@Intercepts注解得到的signature信息，即要拦截的类型与方法
    Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
	  // 获取目标对象（被代理对象）的类型
    Class<?> type = target.getClass();
	  // 获取目标对象实现的接口（拦截器可以拦截4大对象实现的接口）。逐级寻找被代理对象类型的父类，将父类中需要被拦截的全部找出
    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    if (interfaces.length > 0) {
      // 使用jdk的方式创建动态代理对象，是Plugin类的实例
      return Proxy.newProxyInstance(
          type.getClassLoader(),
          interfaces,
          new Plugin(target, interceptor, signatureMap));
    }
    // 直接返回原有被代理对象，这意味着被代理对象的方法不需要被拦截
    return target;
  }

  /**
   * 代理对象的拦截方法，当被代理对象中方法被触发时会进入这里
   * @param proxy 代理类
   * @param method 被触发的方法
   * @param args 被触发的方法的参数
   * @return 被触发的方法的返回结果
   * @throws Throwable
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      // 获取当前接口所有需要拦截的方法
      Set<Method> methods = signatureMap.get(method.getDeclaringClass());
	    // 如果当前方法需要被拦截，则调用interceptor.intercept方法进行拦截处理
      if (methods != null && methods.contains(method)) {
        // 该方法确实需要被拦截器拦截，因此交给拦截器处理
        return interceptor.intercept(new Invocation(target, method, args));
      }
      // 如果当前方法不需要被拦截，则调用对象自身的方法（被代理对象处理）
      return method.invoke(target, args);
    } catch (Exception e) {
      throw ExceptionUtil.unwrapThrowable(e);
    }
  }

  /**
   * 获取拦截器所配置需要拦截的所有类和类中的方法的信息
   * @param interceptor 拦截器
   * @return
   */
  private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
	  // 获取拦截器中的@Intercepts注解实例
    Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
    // issue #251
    if (interceptsAnnotation == null) {
      throw new PluginException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
    }

    // 将 Intercepts 注解的value信息取出来，是一个 Signature 数组。获取其中@Signature实例，并将里面的method信息添加至signatureMap中
    Signature[] sigs = interceptsAnnotation.value();
    // 将 Signature 数组放入一个Map中，键为 Signature 注解的type类型，值为该类型下的方法集合
    Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
    for (Signature sig : sigs) {
      Set<Method> methods = signatureMap.computeIfAbsent(sig.type(), k -> new HashSet<>());
      try {
        Method method = sig.type().getMethod(sig.method(), sig.args());
        methods.add(method);
      } catch (NoSuchMethodException e) {
        throw new PluginException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
      }
    }
    return signatureMap;
  }

  /**
   * 逐级寻找目标类的父类，判断是否有父类需要被拦截器拦截
   * @param type 目标类类型
   * @param signatureMap 拦截器要拦截的所有类和类中的方法
   * @return 拦截器要拦截的所有父类的列表
   */
  private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
    Set<Class<?>> interfaces = new HashSet<>();
    while (type != null) {
      for (Class<?> c : type.getInterfaces()) {
        if (signatureMap.containsKey(c)) {
          interfaces.add(c);
        }
      }
      type = type.getSuperclass();
    }
    return interfaces.toArray(new Class<?>[interfaces.size()]);
  }

}
