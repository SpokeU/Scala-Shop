package actors

import akka.actor.Actor
import pageobject.CategoryPage
import pageobject.ProductInfoPage
import akka.actor.Props
import play.api.Logger
import akka.actor.ActorLogging
import scala.collection.mutable.Map

case class ParseCategoryProductsMessage(requestId: String, categories: CategoryPage)
case class ParseCategoriesProductsMessage(requestId: String, categories: Seq[CategoryPage])

class CategoryPageParserActor extends Actor {

  val log = Logger("parser")

  val productParser = context.actorOf(Props[ProductPageParserActor], name = "Product_page_parser")

  var totalProducts: Map[String, Long] = Map()
  var processedProducts: Map[String, Long] = Map()
  
  var totalCateogries = 0

  override def receive = { 
    case ParseCategoryProductsMessage(requestId, category)     => parseCategory(requestId, category)
    case ParseCategoriesProductsMessage(requestId, categories) => categories.map { parseCategory(requestId, _) }
    case ParseProductsResponse(requestId, success, failed) => {
      println(s"-----------------------------------$processedProducts")
      processedProducts(requestId) += success.size + failed.size
      log.info(s"Products left ${totalProducts(requestId) - processedProducts(requestId)}")
      if (totalProducts(requestId) == processedProducts(requestId)) {
        log.info(s"JobDone for request : $requestId. Total processed products: $processedProducts")
      }
    }
    case _ => println("Govno received")
  }

  def parseCategory(requestId: String, category: CategoryPage) = {
    val products = category.products 
    processedProducts.getOrElseUpdate(requestId, 0)
    totalProducts.getOrElseUpdate(requestId, 0) 
    log.info(s"Category: ${category.name} ; Products count: ${products.size}")
    products match {
      case Nil => {
        category.subCategories.map { self ! ParseCategoryProductsMessage(requestId, _) }
      }
      case products: Seq[ProductInfoPage] if products != Nil => {
        totalProducts(requestId) += products.size
        productParser ! ParseProductsPageMessage(requestId, products)
      }
    }
  }

}