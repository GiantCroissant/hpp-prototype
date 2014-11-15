import akka.actor.{ ActorSystem, Props }
import akka.event.Logging
import akka.io.IO
import spray.can.Http

object Main extends App {
  implicit val system = ActorSystem("test")
  val log = Logging(system, getClass)

  log.info("Starting service actor and HTTP server...")
  val service = system.actorOf(Props(new MyServiceActor()), "test-service")
  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 3000)
}
