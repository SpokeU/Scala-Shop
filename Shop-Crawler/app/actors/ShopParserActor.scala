package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import config.Shop
import akka.actor.Props

case class StartParse(shop: Shop)

class ShopParserActor extends Actor with ActorLogging {

  override def receive = {
    case StartParse(shop)                => context.actorOf(Props[MainPageParserActor]) ! ParseMainPageMessage(shop)
    case ParseMainPageResponse(mainPage) => log.info(s"Shop categories : ${mainPage.categories}")
    case _                               =>
  }

}