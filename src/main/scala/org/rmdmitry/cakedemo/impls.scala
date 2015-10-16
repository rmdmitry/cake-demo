package org.rmdmitry.cakedemo

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by dmitry on 16/10/15.
 */
class CustomerDAOService extends CustomerService {
  override def name(id: Long): Future[Option[String]] = 
    CustomerDAO.executeSQLForFetchingCustomerName(id)
}

class CustomerCachedService extends CustomerService {
  this: CustomerComponent with CacheComponent =>
  
  override def name(id: Long): Future[Option[String]] = cacheService.get[String](id.toString) flatMap {
    case Some(name) => Future.successful(Some(name))
    case None =>
      customerService.name(id) map {
        case Some(name) => 
          cacheService.put(id.toString, name, 60 minutes)
          Some(name)
        case None => None
      }
  } 
}

class RedisCacheImpl extends CacheService {
  override def put[T](key: String, value: T, ttl: Duration): Unit = ???

  override def get[T](key: String): Future[Option[T]] = ???
}

class LocalCacheImpl extends CacheService {
  override def put[T](key: String, value: T, ttl: Duration): Unit = ???

  override def get[T](key: String): Future[Option[T]] = ???
}


