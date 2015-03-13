package actors

import akka.actor.Actor
import config.Shop
import pageobject.deshevshe.DeshevseMainPage
import config.Shop.ROZETKA
import config.Shop.DESHEVSHE
import config.Shop.AMAZON
import pageobject.MainPage
import akka.actor.ActorLogging
import akka.actor.Props
import play.api.Logger

case class ParseMainPageMessage(shop: Shop)
case class ParseMainPageResponse(page: MainPage)

/**
 * Pases main page of shop for the given url and perform something
 */
class MainPageParserActor extends Actor with ActorLogging {

  override def receive = {
    case ParseMainPageMessage(shop) => {
      val mainPage = getMainPage(shop)
      log.info(s"Main page for ${shop.url} parsed")
      val cateogryParser = context.actorOf(Props[CategoryPageParserActor])
      mainPage.categories.map { cateogryParser ! ParseCategoryProductsMessage(_) }
      sender ! ParseMainPageResponse(mainPage)
    }
    case _ => log.error("Unknown message revieved")
  }

  def getMainPage(shop: Shop): MainPage = {
    shop match {
      case ROZETKA   => ???
      case DESHEVSHE => DeshevseMainPage(shop.url)
      case AMAZON    => ???
    }
  }

}