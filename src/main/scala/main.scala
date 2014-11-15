import akka.actor.{ ActorSystem, Props }
import akka.event.Logging
import akka.io.IO
import spray.can.Http
import com.typesafe.config._

object Main extends App {
  implicit val system = ActorSystem("test")
  val log = Logging(system, getClass)

  val conf = ConfigFactory.load()
  val localServer = conf.getBoolean("LOCAL_DEBUG")
  val host = if (localServer) "localhost" else "0.0.0.0"
  val port = if (localServer) 3000 else System.getenv("PORT").toInt

  val service = system.actorOf(Props(new MyServiceActor()), "test-service")
  IO(Http) ! Http.Bind(service, interface = host, port = port)
}
