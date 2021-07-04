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
package org.apache.ibatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * SPI for cache providers.
 * <p>
 * One instance of cache will be created for each namespace.
 * <p>
 * The cache implementation must have a constructor that receives the cache id as an String parameter.
 * <p>
 * MyBatis will pass the namespace as id to the constructor.
 *
 * <pre>
 * public MyCache(final String id) {
 *  if (id == null) {
 *    throw new IllegalArgumentException("Cache instances require an ID");
 *  }
 *  this.id = id;
 *  initialize();
 * }
 * </pre>
 *
 * Cache接口的源码如代码所示，在接口中定义了实现类和装饰器类中必须实现的方法
 *
 * @author Clinton Begin
 */
public interface Cache {

  /**
   * 获取缓存现类的id
   * @return The identifier of this cache 缓存id
   */
  String getId();

  /**
   * 向缓存写入一条数据，key一般是CacheKey对象
   * @param key Can be any object but usually it is a {@link CacheKey} 数据的键
   * @param value The result of a select. 数据的值
   */
  void putObject(Object key, Object value);

  /**
   * 根据指定的key从缓存中读取一条数据
   * @param key The key 数据的键
   * @return The object stored in the cache. 原来的数据值
   */
  Object getObject(Object key);

  /**
   * As of 3.3.0 this method is only called during a rollback
   * for any previous value that was missing in the cache.
   * This lets any blocking cache to release the lock that
   * may have previously put on the key.
   * A blocking cache puts a lock when a value is null
   * and releases it when the value is back again.
   * This way other threads will wait for the value to be
   * available instead of hitting the database.
   *
   * 根据指定的key从缓存中删除一条数据
   *
   * @param key The key 数据的键
   * @return Not used 原来的数据值
   */
  Object removeObject(Object key);

  /**
   * Clears this cache instance.
   * 清空缓存
   */
  void clear();

  /**
   * Optional. This method is not called by the core.
   * 读取缓存中数据数目
   *
   * @return The number of elements stored in the cache (not its capacity). 数据的数目
   */
  int getSize();

  /**
   * Optional. As of 3.2.6 this method is no longer called by the core.
   * <p>
   * Any locking needed by the cache must be provided internally by the cache provider.
   *
   * 获取读写锁，该方法已废弃
   *
   * @return A ReadWriteLock 读写锁
   */
  default ReadWriteLock getReadWriteLock() {
    return null;
  }

}
