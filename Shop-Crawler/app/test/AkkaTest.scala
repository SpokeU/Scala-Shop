package akkatest

import java.util.UUID

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.routing.GetRoutees
import akka.routing.RoundRobinPool
import test.FileParser
import test.ParseFile

class A extends Actor with ActorLogging {

  val brouter = context.actorOf(Props[B].withRouter(RoundRobinPool(nrOfInstances = 2)), name = "B-router")

  def receive = {
    case s: String => {
      log.info("A received")
      1 to 3 foreach { x => brouter ! "attack" }
    }
  }
}

class B extends Actor with ActorLogging {

  val crouter = context.actorOf(Props[C].withRouter(RoundRobinPool(nrOfInstances = 3)), name = "C-router")
  var firstSender: ActorRef = null

  def receive = {
    case s: String => {
      1 to 5 foreach { x => crouter ! "c GO" }
    }
  }
}

class C extends Actor with ActorLogging {

  var firstSender: ActorRef = null

  def receive = {
    case s: String => {
      if (firstSender != null) {
        GetRoutees
        if (!firstSender.path.equals(sender.path)) { log.info(s"---------\\n First sender $firstSender currect sender $sender") }
      } else {
        firstSender = sender
      }
      log.info("B received from" + sender.path)
    }
  }
}

object SimpleMain {
  def main(args: Array[String]): Unit = {
    Nil.size
    val requestUUD = UUID.randomUUID().toString()

    val fileLocation: String = "D:\\akkaTestWordsFile.txt"
    val system = ActorSystem("FileParserSystem")
    val fileParser = system.actorOf(Props[FileParser])
    val testRouters = system.actorOf(Props[A])
    //fileParser ! ParseFile(requestUUD, fileLocation)

    /*val system = ActorSystem("CrawlerSystem"
    val simpleActor = system.actorOf(Props[ShopParserActor], name = "parserActor") /*.withRouter(RoundRobinPool(nrOfInstances = 10))*/
    simpleActor ! StartParse(DESHEVSHE)
    simpleActor ! Broadcast(Message("Hello, Akka!"))
    simpleActor ! Broadcast(PoisonPill)
    simpleActor ! Message("Boy, that was some tasty arsenic!")*/
  }
}

