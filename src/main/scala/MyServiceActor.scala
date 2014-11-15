import spray.http._
import spray.routing._
import StatusCodes._

// Play-ReactiveMongo imports
import play.api.libs.json._
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.json.BSONFormats._

import scala.concurrent.Future
import scala.util.{ Success, Failure }
import scala.concurrent.ExecutionContext.Implicits.global

import spray.httpx.PlayJsonSupport._
import spray.httpx.unmarshalling._
import spray.httpx.marshalling._

// Akka imports
import akka.event.Logging

// ------------------------------------------ //
// Using case classes + Json Writes and Reads //
// ------------------------------------------ //
//import play.api.data.Form
//import JsonFormats._

// ReactiveMongo imports
import reactivemongo.api.MongoDriver


class MyServiceActor extends HttpServiceActor {
  val log = Logging(context.system, getClass)

  // a mongoDriver instance manages an actor system
  // using existing actorySystem -- context.system
  val driver = new MongoDriver(context.system)

  // a connect manages a pool of connections
  val connection = driver.connection(List("localhost"))

  // gets a reference to the database "spray-reactivemongo-textsearch"
  val db = connection.db("spray-reactivemongo-textsearch")
  val testDatas = db.collection("testDatas")

  def receive = runRoute {
    complete("HELLO SPRAY")
  }
}
