package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.actorRef2Scala
import config.AMAZON
import config.DESHEVSHE
import config.ROZETKA
import config.Shop
import pageobject.MainPage
import pageobject.deshevshe.DeshevseMainPage
import play.api.Logger
import scala.collection.mutable._
import akka.routing.RoundRobinPool

case class ParseMainPage(requestId: String, mainPage: MainPage)
case class ParseMainPageResponse(requestId: String, page: MainPage)

/**
 * Pases main page of shop for the given url and perform something
 */
class MainPageParserActor extends Actor {

  val categoryParser = context.actorOf(Props[CategoryPageParserActor], name = "categoryParser")
  val log = Logger("parser")

  val totalCategories: Map[String, Int] = Map()
  val processedCategories: Map[String, Int] = Map()

  override def receive = {
    case ParseMainPage(requestId, mainPage) => {
      val mainCategories = mainPage.categories
      
      totalCategories += requestId -> mainCategories.size
      processedCategories += requestId -> 0
      log.info(s"Total top categories $totalCategories")
      categoryParser ! ParseCategoryProductsMessage(requestId, mainCategories.head)
    }
    case _ => log.error("Unknown message revieved")
  }

}

