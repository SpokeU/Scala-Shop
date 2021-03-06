package parser

import JobResultsAggregator.cleanJobResults
import config.AMAZON
import config.DESHEVSHE
import config.ROZETKA
import config.Shop
import pageobject.MainPage
import pageobject.deshevshe.DeshevseMainPage

object ShopParser {

  def parseShop(shop: Shop)(implicit jobId: Int) = {
    cleanJobResults
    val mainPage = getMainPage(shop)
    CategoryParser.parse(mainPage.categories.head)

  }

  private def getMainPage(shop: Shop): MainPage = {
    shop match {
      case ROZETKA   => ???
      case DESHEVSHE => DeshevseMainPage(shop.url)
      case AMAZON    => ???
    }

  }
}