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

// config
import com.typesafe.config._

// ------------------------------------------ //
// Using case classes + Json Writes and Reads //
// ------------------------------------------ //
import play.api.data.Form
import JsonFormats._

// ReactiveMongo imports
import reactivemongo.api.MongoConnection._
import reactivemongo.api.MongoConnectionOptions
import reactivemongo.api.MongoDriver
import reactivemongo.core.nodeset._

class MyServiceActor extends HttpServiceActor {
  val log = Logging(context.system, getClass)
  val conf = ConfigFactory.load()

  val driver = new MongoDriver(context.system)
  implicit val connection = if (conf.getBoolean("LOCAL_DEBUG")) {
    driver.connection(List("localhost"))

  } else {
    // mongodb://<user>:<password>@dogen.mongohq.com:10063/app31630643
    //val pattern = "^mongodb:\\/\\/([\\w]+):([\\w]+)@([\\w\\.]+):([\\d]+)\\/([\\w]+)".r
    //val pattern(user, password, host, port, db) = conf.getString("mongodb.uri")

    val user = "heroku"
    val password = "J3Owg3wMhsa9IW4G-Ag7AK3dBpqEvKx1qy_KsKcowVg159Ll6xFj6JX5Ge-BwxXKZhJxzPOZAB1oycCxGt5DuA"
    val host = "dogen.mongohq.com"
    val port = 10063
    val db = "app31630643"

    driver.connection(
      new ParsedURI(List((host, port)),
      new MongoConnectionOptions(1000),
      List(),
      Some(db),
      Some(new Authenticate(db, user, password))))
  }

  // gets a reference to the database "spray-reactivemongo-textsearch"
  val db = connection.db("spray-reactivemongo-textsearch")
  val testDatas = db.collection("testDatas")

  def receive = runRoute {
    path("/") {
      get {
        complete {
          "Hello HPP!!"
        }
      }
    }~
    path("products") {
      get {
        complete {
          var query = Json.obj()
          val collection = db.collection[JSONCollection]("products")
          val products = collection.find(query).cursor[Product].collect[List]()
          products.map { lists =>
            Json.obj("results" -> lists)
          }
        }
      }
    }
  }
}
