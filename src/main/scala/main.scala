import akka.actor.{ ActorSystem, Props }
import akka.event.Logging
import akka.io.IO
import spray.can.Http

object Main extends App {
  implicit val system = ActorSystem("test")
  val log = Logging(system, getClass)

  val SERVER_HOST = "0.0.0.0"
  val SERVER_PORT = Option(System.getenv("PORT")).getOrElse("8080").toInt

  log.info("Starting service actor and HTTP server...")
  val service = system.actorOf(Props(new MyServiceActor()), "test-service")
  IO(Http) ! Http.Bind(service, interface = SERVER_HOST, port = SERVER_PORT)
}
