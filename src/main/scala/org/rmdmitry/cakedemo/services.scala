package org.rmdmitry.cakedemo

import scala.concurrent.Future
import scala.concurrent.duration.Duration

/**
 * Created by dmitry on 16/10/15.
 */

/*
      Customer Service
 */

trait CustomerService {
  def name(id: Long): Future[Option[String]]
}

trait CustomerComponent {
  def customerService: CustomerService
}

/*
    Cache Service
 */

trait CacheService {
  def put[T](key: String, value: T, ttl: Duration): Unit

  def get[T](key: String): Future[Option[T]]
}

trait CacheComponent {
  def cacheService: CacheService
}
