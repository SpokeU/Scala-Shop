package actors

import akka.actor.Actor
import pageobject.ProductInfoPage

case class ParseProductPageMessage(product: ProductInfoPage)

class ProductPageParserActor extends Actor {

  override def receive = {
    case ParseProductPageMessage(product) => 
    case _ =>
  }

}