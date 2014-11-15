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
import scala.util.Try

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
import reactivemongo.api.{ MongoDriver, MongoConnection }


class MyServiceActor extends HttpServiceActor {
  val log = Logging(context.system, getClass)

  // a mongoDriver instance manages an actor system
  // using existing actorySystem -- context.system
  val driver = new MongoDriver(context.system)

  val uri = "mongodb://heroku:J3Owg3wMhsa9IW4G-Ag7AK3dBpqEvKx1qy_KsKcowVg159Ll6xFj6JX5Ge-BwxXKZhJxzPOZAB1oycCxGt5DuA@dogen.mongohq.com:10063/app31630643"

  // a connect manages a pool of connections
  val connection = MongoConnection.parseURI(uri).map { parsedUri =>
    driver.connection(parsedUri)

  } getOrElse {
    driver.connection(List("localhost"))
  }

  log.info(connection)

  // gets a reference to the database "spray-reactivemongo-textsearch"
  val db = connection.db("spray-reactivemongo-textsearch")
  val testDatas = db.collection("testDatas")

  def receive = runRoute {
    complete("HELLO SPRAY")
  }
}
