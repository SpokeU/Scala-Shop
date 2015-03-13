package pageobject.deshevshe

import pageobject.ProductInfoPage
import org.jsoup.nodes.Element
import webclient.WebClient

class DeshevseProductInfoPage(val source: Any) extends ProductInfoPage {

  def name: String = {
    page.select("[itemprop=name]").first().text()
  }

  def price: BigDecimal = {
    ???
  }

  def description: String = {
    ???
  }

  def details: String = {
    ???
  }

  def characteristics: Map[String, String] = {
    ???
  }

  def mainImage: String = {
    ???
  }

  def mediumImages: Seq[String] = {
    ???
  }

  def bigImages: Seq[String] = {
    ???
  }

}