package actors

import akka.actor.Actor
import pageobject.CategoryPage
import pageobject.ProductInfoPage

case class ParseCategoryProductsMessage(categories: CategoryPage)

class CategoryPageParserActor extends Actor {

  override def receive = {
    case ParseCategoryProductsMessage(category) => {
      val products = category.products
      products match {
        case products: Seq[ProductInfoPage] => products.map { product =>
        }
        case Nil =>
          category.subCategories.map { self ! ParseCategoryProductsMessage(_) }
      }
    }
    case _ =>
  }

}