package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.actorRef2Scala
import pageobject.ProductInfoPage
import play.api.Logger
import scala.collection.mutable.LinkedList
import scala.collection.mutable.ArrayBuffer

case class ParseProductPageMessage(requestId: String, product: ProductInfoPage)
case class ParseProductsPageMessage(requestId: String, product: Seq[ProductInfoPage])
case class ParseProductsResponse(requestId: String, success: Seq[ProductInfoPage], failed: Seq[ProductInfoPage])

class ProductPageParserActor extends Actor {

  val log = Logger("parser")

  override def receive = {
    case ParseProductPageMessage(requestId, product) => {
      try {
        parseProduct(product)
        sender ! ParseProductsResponse(requestId, List(product), Nil)
      } catch {
        case _: Exception => sender ! ParseProductsResponse(requestId, Nil , List(product))
      }

    }
    case ParseProductsPageMessage(requestId, products) => {
      var processed: ArrayBuffer[ProductInfoPage] = ArrayBuffer()
      var failed: ArrayBuffer[ProductInfoPage] = ArrayBuffer()
      products.map { productPage =>
        try {
          parseProduct(productPage)
          processed += (productPage)
        } catch {
          case _: Exception => failed += (productPage)
        }
      }
      sender ! ParseProductsResponse(requestId, processed, failed)
    }
    case _ => println("Govno received")
  }

  def parseProduct(productPage: ProductInfoPage) = {
    log.info(s"Product: ${productPage.name}")
  }

}