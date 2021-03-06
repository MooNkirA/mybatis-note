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
package org.apache.ibatis.builder.xml;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.JdbcType;

/**
 * @author Clinton Begin
 * @author Kazuki Shimizu
 */
public class XMLConfigBuilder extends BaseBuilder {

  // 是否完成解析
  private boolean parsed;
  // 解析器
  private final XPathParser parser;
  // 要读取哪一个Environment节点，这里存储节点名称
  private String environment;
  // 反射工厂
  private final ReflectorFactory localReflectorFactory = new DefaultReflectorFactory();

  public XMLConfigBuilder(Reader reader) {
    this(reader, null, null);
  }

  public XMLConfigBuilder(Reader reader, String environment) {
    this(reader, environment, null);
  }

  public XMLConfigBuilder(Reader reader, String environment, Properties props) {
    this(new XPathParser(reader, true, props, new XMLMapperEntityResolver()), environment, props);
  }

  public XMLConfigBuilder(InputStream inputStream) {
    this(inputStream, null, null);
  }

  public XMLConfigBuilder(InputStream inputStream, String environment) {
    this(inputStream, environment, null);
  }

  public XMLConfigBuilder(InputStream inputStream, String environment, Properties props) {
    // 先创建XPathParser实例，然后XMLConfigBuilder类的自己的构造函数
    this(new XPathParser(inputStream, true, props, new XMLMapperEntityResolver()), environment, props);
  }

  private XMLConfigBuilder(XPathParser parser, String environment, Properties props) {
    // 在这里创建了一个核心类Configuration的实例，调用抽象父类BaseBuilder构造函数，设置成员变量configuration
    super(new Configuration());
    ErrorContext.instance().resource("SQL Mapper Configuration");
    this.configuration.setVariables(props);
    this.parsed = false;
    this.environment = environment;
    this.parser = parser;
  }

  /**
   * 解析配置文件的入口
   * @return Configuration 对象
   */
  public Configuration parse() {
    // 判断标识，不允许重复解析
    if (parsed) {
      throw new BuilderException("Each XMLConfigBuilder can only be used once.");
    }
    // 设置解析标识
    parsed = true;
    // 从根节点<configuration>标签开始解析
    parseConfiguration(parser.evalNode("/configuration"));
    return configuration;
  }

  /**
   * 从根节点configuration开始解析下层节点
   * @param root 根节点configuration节点
   */
  /* 当前类中有一个Configuration类的成员属性，在此方法中，所有解析信息的都放入此Configuration实例中 */
  private void parseConfiguration(XNode root) {
    try {
      // 首先解析<properties>标签，以保证在解析其他节点时可以会用到properties中的参数值
      //issue #117 read properties first
      propertiesElement(root.evalNode("properties"));
      // 解析<settings>标签
      Properties settings = settingsAsProperties(root.evalNode("settings"));
      // 获取 <setting> 标签name属性为vfsImpl的值，加载自定义类扫描器(类似spring的类扫描器)
      loadCustomVfs(settings);
      // 获取 <setting> 标签name属性为logImpl的值
      loadCustomLogImpl(settings);
      // 别名标签 <typeAliases> 扫描注册【重点】
      typeAliasesElement(root.evalNode("typeAliases"));
      // 插件标签 <plugins> 的解析
      pluginElement(root.evalNode("plugins"));
      // 解析Pojo对象工厂类。可以用于自定义数据库与pojo对象的映射逻辑，一般都使用默认的【不重要】
      objectFactoryElement(root.evalNode("objectFactory"));
      objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
      reflectorFactoryElement(root.evalNode("reflectorFactory"));
      // 解析settings标签
      settingsElement(settings);
      // read it after objectFactory and objectWrapperFactory issue #631
      // 解析环境标签
      environmentsElement(root.evalNode("environments"));
      // 解析数据库厂商标识（databaseIdProvider）
      databaseIdProviderElement(root.evalNode("databaseIdProvider"));
      // 解析类型转换器，即解析<typeHandlers>标签，注册（建立）类型处理器的映射关系
      typeHandlerElement(root.evalNode("typeHandlers"));
      // 解析mappers映射器标签
      mapperElement(root.evalNode("mappers"));
    } catch (Exception e) {
      throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
    }
  }

  private Properties settingsAsProperties(XNode context) {
    if (context == null) {
      return new Properties();
    }
    Properties props = context.getChildrenAsProperties();
    // Check that all settings are known to the configuration class
    MetaClass metaConfig = MetaClass.forClass(Configuration.class, localReflectorFactory);
    for (Object key : props.keySet()) {
      if (!metaConfig.hasSetter(String.valueOf(key))) {
        throw new BuilderException("The setting " + key + " is not known.  Make sure you spelled it correctly (case sensitive).");
      }
    }
    return props;
  }

  private void loadCustomVfs(Properties props) throws ClassNotFoundException {
    String value = props.getProperty("vfsImpl");
    if (value != null) {
      // 配置自定义 VFS 是多个实现类全限定名，以逗号分隔。
      String[] clazzes = value.split(",");
      for (String clazz : clazzes) {
        if (!clazz.isEmpty()) {
          @SuppressWarnings("unchecked")
          Class<? extends VFS> vfsImpl = (Class<? extends VFS>)Resources.classForName(clazz);
          // 将自定义 VFS 实现类的Class对象保存到Configuration类中
          configuration.setVfsImpl(vfsImpl);
        }
      }
    }
  }

  private void loadCustomLogImpl(Properties props) {
    // 获取 <setting> 标签name属性为logImpl的值
    Class<? extends Log> logImpl = resolveClass(props.getProperty("logImpl"));
    // 保存到Configuration类中
    configuration.setLogImpl(logImpl);
  }

  private void typeAliasesElement(XNode parent) {
    if (parent != null) {
      // 循环<typeAliases>标签下所有子标签
      for (XNode child : parent.getChildren()) {
        if ("package".equals(child.getName())) {
          /* <package>标签，重点关注此分支，一般都是配置包扫描 */
          String typeAliasPackage = child.getStringAttribute("name");
          // 从Configuration类中获取别名注册器TypeAliasRegistry，根据包路径扫描并注册相应的类与别名的映射
          configuration.getTypeAliasRegistry().registerAliases(typeAliasPackage);
        } else {
          /* <typeAlias>标签 */
          String alias = child.getStringAttribute("alias");
          String type = child.getStringAttribute("type");
          try {
            // 获取Class对象
            Class<?> clazz = Resources.classForName(type);
            // 注册别名与Class对象的映射
            if (alias == null) {
              typeAliasRegistry.registerAlias(clazz);
            } else {
              typeAliasRegistry.registerAlias(alias, clazz);
            }
          } catch (ClassNotFoundException e) {
            throw new BuilderException("Error registering typeAlias for '" + alias + "'. Cause: " + e, e);
          }
        }
      }
    }
  }

  /**
   * 解析<plugins>节点，组成拦截器链
   *
   * @param parent <plugins>节点
   * @throws Exception
   */
  private void pluginElement(XNode parent) throws Exception {
    // 如配置中有 <plugins> 节点
    if (parent != null) {
      // 依次取出<plugins>节点下的每个<plugin>节点
      for (XNode child : parent.getChildren()) {
        // 读取拦截器名称
        String interceptor = child.getStringAttribute("interceptor");
        // 读取拦截器属性
        Properties properties = child.getChildrenAsProperties();
        // 实例化拦截器类，resolveClass方法会根据配置中的interceptor属性去匹配相应的别名，获取Class对象，再反射获取类实例
        Interceptor interceptorInstance = (Interceptor) resolveClass(interceptor).newInstance();
        // 设置拦截器属性
        interceptorInstance.setProperties(properties);
        // 将当前拦截器加入到拦截器链中（存储在Configuration类）
        configuration.addInterceptor(interceptorInstance);
      }
    }
  }

  private void objectFactoryElement(XNode context) throws Exception {
    if (context != null) {
      String type = context.getStringAttribute("type");
      Properties properties = context.getChildrenAsProperties();
      ObjectFactory factory = (ObjectFactory) resolveClass(type).newInstance();
      factory.setProperties(properties);
      configuration.setObjectFactory(factory);
    }
  }

  private void objectWrapperFactoryElement(XNode context) throws Exception {
    if (context != null) {
      String type = context.getStringAttribute("type");
      ObjectWrapperFactory factory = (ObjectWrapperFactory) resolveClass(type).newInstance();
      configuration.setObjectWrapperFactory(factory);
    }
  }

  private void reflectorFactoryElement(XNode context) throws Exception {
    if (context != null) {
      String type = context.getStringAttribute("type");
      ReflectorFactory factory = (ReflectorFactory) resolveClass(type).newInstance();
      configuration.setReflectorFactory(factory);
    }
  }

  private void propertiesElement(XNode context) throws Exception {
    if (context != null) {
      Properties defaults = context.getChildrenAsProperties();
      String resource = context.getStringAttribute("resource");
      String url = context.getStringAttribute("url");
      if (resource != null && url != null) {
        throw new BuilderException("The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other.");
      }
      if (resource != null) {
        defaults.putAll(Resources.getResourceAsProperties(resource));
      } else if (url != null) {
        defaults.putAll(Resources.getUrlAsProperties(url));
      }
      Properties vars = configuration.getVariables();
      if (vars != null) {
        defaults.putAll(vars);
      }
      parser.setVariables(defaults);
      // 将解析出来的properties对象，设置到Configuration实例中
      configuration.setVariables(defaults);
    }
  }

  /* 逐个<setting>标签设置值到Configuration类中 */
  private void settingsElement(Properties props) {
    configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(props.getProperty("autoMappingBehavior", "PARTIAL")));
    configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.valueOf(props.getProperty("autoMappingUnknownColumnBehavior", "NONE")));
    configuration.setCacheEnabled(booleanValueOf(props.getProperty("cacheEnabled"), true));
    configuration.setProxyFactory((ProxyFactory) createInstance(props.getProperty("proxyFactory")));
    configuration.setLazyLoadingEnabled(booleanValueOf(props.getProperty("lazyLoadingEnabled"), false));
    configuration.setAggressiveLazyLoading(booleanValueOf(props.getProperty("aggressiveLazyLoading"), false));
    configuration.setMultipleResultSetsEnabled(booleanValueOf(props.getProperty("multipleResultSetsEnabled"), true));
    configuration.setUseColumnLabel(booleanValueOf(props.getProperty("useColumnLabel"), true));
    configuration.setUseGeneratedKeys(booleanValueOf(props.getProperty("useGeneratedKeys"), false));
    configuration.setDefaultExecutorType(ExecutorType.valueOf(props.getProperty("defaultExecutorType", "SIMPLE")));
    configuration.setDefaultStatementTimeout(integerValueOf(props.getProperty("defaultStatementTimeout"), null));
    configuration.setDefaultFetchSize(integerValueOf(props.getProperty("defaultFetchSize"), null));
    configuration.setDefaultResultSetType(resolveResultSetType(props.getProperty("defaultResultSetType")));
    configuration.setMapUnderscoreToCamelCase(booleanValueOf(props.getProperty("mapUnderscoreToCamelCase"), false));
    configuration.setSafeRowBoundsEnabled(booleanValueOf(props.getProperty("safeRowBoundsEnabled"), false));
    configuration.setLocalCacheScope(LocalCacheScope.valueOf(props.getProperty("localCacheScope", "SESSION")));
    configuration.setJdbcTypeForNull(JdbcType.valueOf(props.getProperty("jdbcTypeForNull", "OTHER")));
    configuration.setLazyLoadTriggerMethods(stringSetValueOf(props.getProperty("lazyLoadTriggerMethods"), "equals,clone,hashCode,toString"));
    configuration.setSafeResultHandlerEnabled(booleanValueOf(props.getProperty("safeResultHandlerEnabled"), true));
    configuration.setDefaultScriptingLanguage(resolveClass(props.getProperty("defaultScriptingLanguage")));
    configuration.setDefaultEnumTypeHandler(resolveClass(props.getProperty("defaultEnumTypeHandler")));
    configuration.setCallSettersOnNulls(booleanValueOf(props.getProperty("callSettersOnNulls"), false));
    configuration.setUseActualParamName(booleanValueOf(props.getProperty("useActualParamName"), true));
    configuration.setReturnInstanceForEmptyRow(booleanValueOf(props.getProperty("returnInstanceForEmptyRow"), false));
    configuration.setLogPrefix(props.getProperty("logPrefix"));
    configuration.setConfigurationFactory(resolveClass(props.getProperty("configurationFactory")));
  }

  private void environmentsElement(XNode context) throws Exception {
    if (context != null) {
      if (environment == null) {
        // 获取default属性的值
        environment = context.getStringAttribute("default");
      }
      for (XNode child : context.getChildren()) {
        // 获取子标签<environment>的id属性
        String id = child.getStringAttribute("id");
        // 判断id是否与父标签中的default属性一致
        if (isSpecifiedEnvironment(id)) {
          // 根据别名，分别获取transactionManager与dataSource配置的相应实现类
          TransactionFactory txFactory = transactionManagerElement(child.evalNode("transactionManager"));
          DataSourceFactory dsFactory = dataSourceElement(child.evalNode("dataSource"));
          DataSource dataSource = dsFactory.getDataSource();
          // Environment的Builder建造者
          Environment.Builder environmentBuilder = new Environment.Builder(id) // 设置配置的id属性
              .transactionFactory(txFactory) // 设置事务工厂
              .dataSource(dataSource); // 设置数据源信息
          // 创建Environment实例并设置到Configuration类中
          configuration.setEnvironment(environmentBuilder.build());
        }
      }
    }
  }

  private void databaseIdProviderElement(XNode context) throws Exception {
    DatabaseIdProvider databaseIdProvider = null;
    if (context != null) {
      // 获取type属性值
      String type = context.getStringAttribute("type");
      // awful patch to keep backward compatibility
      if ("VENDOR".equals(type)) {
        type = "DB_VENDOR";
      }
      // 获取子标签<property>的配置值，封装成Properties对象
      Properties properties = context.getChildrenAsProperties();
      // 根据type的别名，找到相应的实现类，创建该实例
      databaseIdProvider = (DatabaseIdProvider) resolveClass(type).newInstance();
      // 设置属性值
      databaseIdProvider.setProperties(properties);
    }
    Environment environment = configuration.getEnvironment();
    if (environment != null && databaseIdProvider != null) {
      // 获取当前数据源的类型，最终会返回databaseIdProvider标签的property子标签的value值
      String databaseId = databaseIdProvider.getDatabaseId(environment.getDataSource());
      // 设置到Configuration类中
      configuration.setDatabaseId(databaseId);
    }
  }

  private TransactionFactory transactionManagerElement(XNode context) throws Exception {
    if (context != null) {
      String type = context.getStringAttribute("type");
      Properties props = context.getChildrenAsProperties();
      TransactionFactory factory = (TransactionFactory) resolveClass(type).newInstance();
      factory.setProperties(props);
      return factory;
    }
    throw new BuilderException("Environment declaration requires a TransactionFactory.");
  }

  /**
   * 解析配置信息，获取数据源工厂
   * 被解析的配置信息示例如下：
   * <dataSource type="POOLED">
   *   <property name="driver" value="{dataSource.driver}"/>
   *   <property name="url" value="{dataSource.url}"/>
   *   <property name="username" value="${dataSource.username}"/>
   *   <property name="password" value="${dataSource.password}"/>
   * </dataSource>
   *
   * @param context 被解析的节点
   * @return 数据源工厂
   * @throws Exception
   */
  private DataSourceFactory dataSourceElement(XNode context) throws Exception {
    if (context != null) {
      // 通过这里的类型判断数据源类型，例如POOLED、UNPOOLED、JNDI
      String type = context.getStringAttribute("type");
      // 获取dataSource节点下配置的property标签的值，封装成Properties对象
      Properties props = context.getChildrenAsProperties();
      // 根据dataSource的type值获取相应的DataSourceFactory对象
      DataSourceFactory factory = (DataSourceFactory) resolveClass(type).newInstance();
      // 设置DataSourceFactory对象的属性
      factory.setProperties(props);
      return factory;
    }
    throw new BuilderException("Environment declaration requires a DataSourceFactory.");
  }

  private void typeHandlerElement(XNode parent) {
    if (parent != null) {
      // 循环typeHandlers标签的所有子节点
      for (XNode child : parent.getChildren()) {
        // 判断子节点类型
        if ("package".equals(child.getName())) {
          // package子标签
          String typeHandlerPackage = child.getStringAttribute("name");
          // 根据配置的包路径，去扫描并注册该包下的类型处理器
          typeHandlerRegistry.register(typeHandlerPackage);
        } else {
          // typeHandler子标签
          String javaTypeName = child.getStringAttribute("javaType");
          String jdbcTypeName = child.getStringAttribute("jdbcType");
          String handlerTypeName = child.getStringAttribute("handler");
          // 同样的方式，根据配置的别名去获取相应的Class对象
          Class<?> javaTypeClass = resolveClass(javaTypeName);
          JdbcType jdbcType = resolveJdbcType(jdbcTypeName);
          Class<?> typeHandlerClass = resolveClass(handlerTypeName);
          // 注册类型处理器
          if (javaTypeClass != null) {
            if (jdbcType == null) {
              typeHandlerRegistry.register(javaTypeClass, typeHandlerClass);
            } else {
              typeHandlerRegistry.register(javaTypeClass, jdbcType, typeHandlerClass);
            }
          } else {
            typeHandlerRegistry.register(typeHandlerClass);
          }
        }
      }
    }
  }

  /**
   * 解析mappers节点，例如：
   * <mappers>
   *    <mapper resource="com/github/yeecode/mybatisDemo/UserDao.xml"/>
   *    <package name="com.github.yeecode.mybatisDemo" />
   * </mappers>
   * @param parent mappers节点
   * @throws Exception
   */
  private void mapperElement(XNode parent) throws Exception {
    if (parent != null) {
      // 处理mappers的子节点，即mapper节点或者package节点
      for (XNode child : parent.getChildren()) {
        // package节点
        if ("package".equals(child.getName())) {
          // 取出包路径
          String mapperPackage = child.getStringAttribute("name");
          // 全部加入Mappers中
          configuration.addMappers(mapperPackage);
        } else {
          // 单独配置的mapper节点。注意：resource、url、class这三个属性只有一个生效
          String resource = child.getStringAttribute("resource");
          String url = child.getStringAttribute("url");
          String mapperClass = child.getStringAttribute("class");
          if (resource != null && url == null && mapperClass == null) {
            ErrorContext.instance().resource(resource);
            // 获取文件的输入流
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 使用XMLMapperBuilder解析映射文件
            XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
            mapperParser.parse();
          } else if (resource == null && url != null && mapperClass == null) {
            ErrorContext.instance().resource(url);
            // 从网络获得输入流
            InputStream inputStream = Resources.getUrlAsStream(url);
            // 使用XMLMapperBuilder解析映射文件
            XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, url, configuration.getSqlFragments());
            mapperParser.parse();
          } else if (resource == null && url == null && mapperClass != null) {
            // 配置的不是映射文件，而是映射接口
            Class<?> mapperInterface = Resources.classForName(mapperClass);
            // 直接加到
            configuration.addMapper(mapperInterface);
          } else {
            throw new BuilderException("A mapper element may only specify a url, resource or class, but not more than one.");
          }
        }
      }
    }
  }

  private boolean isSpecifiedEnvironment(String id) {
    if (environment == null) {
      throw new BuilderException("No environment specified.");
    } else if (id == null) {
      throw new BuilderException("Environment requires an id attribute.");
    } else if (environment.equals(id)) {
      return true;
    }
    return false;
  }

}
