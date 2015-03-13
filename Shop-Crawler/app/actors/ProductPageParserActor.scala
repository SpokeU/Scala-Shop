package actors

import akka.actor.Actor
import pageobject.ProductInfoPage
import play.api.Logger
import akka.actor.ActorLogging

case class ParseProductPageMessage(product: ProductInfoPage)
case class ParseProductsPageMessage(product: Seq[ProductInfoPage])

class ProductPageParserActor extends Actor with ActorLogging{

  override def receive = {
    case ParseProductPageMessage(product) => log.info(s"Product: ${product.name}")
    case _ =>
  }

}