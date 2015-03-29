package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import config.Shop
import akka.actor.Props
import akka.actor.PoisonPill
import config.ROZETKA
import config.DESHEVSHE
import config.AMAZON
import pageobject.MainPage
import pageobject.deshevshe.DeshevseMainPage

case class StartParse(requestId: String, shop: Shop)
case class StopParse(requestId: String, shop: Shop)
case class StartParseResponse(response: String)

class ShopParserActor extends Actor with ActorLogging {

  val jobStatus: scala.collection.mutable.Map[Shop, Boolean] = scala.collection.mutable.Map(
    ROZETKA -> false,
    DESHEVSHE -> false,
    AMAZON -> false)

  val mainPageParser = context.actorOf(Props[MainPageParserActor], name = "MainPageParser")

  override def receive = {
    case StartParse(requestId, shop) => {
      if (!jobStatus(shop)) {
        jobStatus(shop) = true
        val mainPage = getMainPage(shop)
        mainPageParser ! ParseMainPage(requestId, mainPage)
        sender ! StartParseResponse("Success")
      } else {
        sender ! StartParseResponse("Failure")
      }
    }
    case StopParse(requestId, shop) => {
      jobStatus(shop) = false
      mainPageParser ! PoisonPill
    }
    case ParseMainPageResponse(requestId, mainPage) => log.info(s"Shop categories size: ${mainPage.categories.size}")
    case _ => log.info("Got undefined message")
  }

  def getMainPage(shop: Shop): MainPage = {
    shop match {
      case ROZETKA   => ???
      case DESHEVSHE => DeshevseMainPage(shop.url)
      case AMAZON    => ???
    }

  }
}