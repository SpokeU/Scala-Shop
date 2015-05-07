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

  /*private def parseCategory(category: CategoryPage)(implicit jobId: Int): Unit = {
    addToTotalCategories
    saveCategory(category)
    category.products match {
      case Nil                            => category.subCategories.map { parseCategory(_) }
      case products: Seq[ProductInfoPage] => products.map { parseProduct(_) }
    }
    addToProcessedCategories
  }

  private def parseProduct(product: ProductInfoPage)(implicit jobId: Int) = {
    addToTotalProducts
    saveProduct(product)
    addToProcessedProducts
  }

  private def saveCategory(category: CategoryPage) = {
    println(s"Saving category ${category.name}")
  }

  private def saveProduct(product: ProductInfoPage) = {
    println(s"Saving product ${product.name}")
  }*/

  private def getMainPage(shop: Shop): MainPage = {
    shop match {
      case ROZETKA   => ???
      case DESHEVSHE => DeshevseMainPage(shop.url)
      case AMAZON    => ???
    }

  }
}