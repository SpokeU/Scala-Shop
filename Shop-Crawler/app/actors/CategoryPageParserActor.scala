package actors

import akka.actor.Actor
import pageobject.CategoryPage

case class ParseCategoriesMessage(categories: Seq[CategoryPage])

class CategoryPageParserActor extends Actor {

  override def receive = {
    case ParseCategoriesMessage(categories) => 
    case _ =>
  }

}