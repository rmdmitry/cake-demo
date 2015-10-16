package org.rmdmitry.cakedemo

/**
 * Created by dmitry on 16/10/15.
 */
object Wired {

  lazy val customerDAOService = new CustomerDAOService

  trait CustomerDAOComponent extends CustomerComponent {
    override def customerService: CustomerService = customerDAOService
  }

  lazy val redisCache = new RedisCacheImpl
  trait RedisCacheComponent extends CacheComponent {
    override def cacheService: CacheService = redisCache
  }

  lazy val localCache = new LocalCacheImpl
  trait LocalCacheComponent extends CacheComponent {
    override def cacheService: CacheService = localCache
  }

  lazy val customerServiceWithRedisCache = new CustomerCachedService
    with RedisCacheComponent with CustomerDAOComponent
  trait CustomeWithRedisCacheComponent extends CustomerComponent {
    override def customerService: CustomerService = customerServiceWithRedisCache
  }

  lazy val customer2LCacheService = new CustomerCachedService with LocalCacheComponent
    with CustomeWithRedisCacheComponent
  trait Customer2LCacheComponent extends CustomerComponent {
    override def customerService: CustomerService = customer2LCacheService
  }
}
