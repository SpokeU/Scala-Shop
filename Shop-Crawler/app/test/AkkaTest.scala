package akkatest

import akka.actor.{ Actor, ActorSystem, PoisonPill, Props, actorRef2Scala }
import akka.event.Logging
import akka.routing.{ Broadcast, RoundRobinRouter }
import play.api.Logger
import akka.routing.RoundRobinPool
import actors.ShopParserActor
import actors.StartParse
import config.Shop.DESHEVSHE

case class Message(msg: String)

class SimpleActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case Message(msg) => log.info(s"Got a valid message: $msg . Object Hash : ${this.hashCode()}")
    case default      => log.error("Got a message I don't understand.")
  }
}

object SimpleMain {
  def main(args: Array[String]): Unit = {
    Logger.error("Hello from crawler")
    val system = ActorSystem("CrawlerSystem")
    val simpleActor = system.actorOf(Props[ShopParserActor], name = "parserActor") /*.withRouter(RoundRobinPool(nrOfInstances = 10))*/
    simpleActor ! StartParse(DESHEVSHE)
    /*simpleActor ! Broadcast(Message("Hello, Akka!"))
    simpleActor ! Broadcast(PoisonPill)
    simpleActor ! Message("Boy, that was some tasty arsenic!")*/
  }
}
