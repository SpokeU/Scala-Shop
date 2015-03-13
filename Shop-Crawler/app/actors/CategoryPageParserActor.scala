package actors

import akka.actor.Actor
import pageobject.CategoryPage
import pageobject.ProductInfoPage
import akka.actor.Props
import play.api.Logger
import akka.actor.ActorLogging

case class ParseCategoryProductsMessage(categories: CategoryPage)

class CategoryPageParserActor extends Actor with ActorLogging {

  val productParser = context.actorOf(Props[ProductPageParserActor], "Product_page_parser")

  override def receive = {
    case ParseCategoryProductsMessage(category) => {
      val products = category.products
      log.info(s"Category: ${category.name} ; Products count: ${products.size}")
      products match {
        case Nil => {
          category.subCategories.map { self ! ParseCategoryProductsMessage(_) }
        }
        case products: Seq[ProductInfoPage] if products != Nil => {
          products.map { productParser ! ParseProductPageMessage(_) }
        }
      }
    }
    case _ =>
  }

}