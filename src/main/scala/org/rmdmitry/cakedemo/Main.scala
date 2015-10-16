package org.rmdmitry.cakedemo

import org.rmdmitry.cakedemo.Wired.Customer2LCacheComponent

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by dmitry on 16/10/15.
 */
object Main extends App {
  val c = new Controller with Customer2LCacheComponent
  Await.ready(c.handleRequest(105l).map { res => println(s"Succeeded + $res") }, 5 seconds)
}

abstract class Controller {
  this: CustomerComponent =>

  def handleRequest(id: Long): Future[Option[String]] = customerService.name(id)
}
