package pageobject.rozetka

import org.jsoup.Jsoup
import pageobject.ProductInfoPage
import scala.collection.JavaConversions._
import org.jsoup.nodes.Document

class RozetkaProductInfoPage(url: String) extends ProductInfoPage {

  import RozetkaProductInfoPage._

  val page = Jsoup.connect(url).get();

  def name: String = {
    page.select(selectors("name")).first.text
  }

  def price: BigDecimal = {
    page.select(selectors("price")).first.text.replaceAll("\\s", "").toInt
  }

  def details: String = {
    page.select(selectors("details")).first.text
  }

  def description: String = {
    page.select(selectors("description")).first.text
  }

  def characteristics: Map[String, String] = {
    val characteristicsPage = getCharacteristicsPage
    val characteristicsElements = characteristicsPage.select(selectors("characteristics"))

    val characteristicsMap = characteristicsElements.map { element =>
      val characteristicName = element.select(selectors("characteristicName")).first().text()
      val characteristicValue = element.select(selectors("characteristicValue")).first().text()
      characteristicName -> characteristicValue
    }.toMap

    characteristicsMap
  }

  def bigImages: List[String] = {
    page.select(".detail-tab-i-img a").map { _.attr("href") }.toList
  }

  def mediumImages: List[String] = {
    page.select(".detail-tab-i-img img").map { _.attr("data_src") }.toList
  }

  def smallImages: List[String] = {
    ???
  }

  def mainImage: String = {
    page.select(selectors("image")).first().attr("src")
  }

  private def getCharacteristicsPage = {
    val allCharacteristicslink = page.getElementsByAttributeValue("name", "all_characteristics").head.attr("href")
    Jsoup.connect(allCharacteristicslink).get
  }

}

object RozetkaProductInfoPage {

  val selectors = Map(
    "name" -> ".detail-title",
    "price" -> "[itemprop=price]",
    "details" -> ".pp-description",
    "description" -> "#short_text",
    "characteristics" -> ".detail-tab-characteristics-i",
    "characteristicName" -> ".detail-tab-characteristics-i-title",
    "characteristicValue" -> ".detail-tab-characteristics-i-field",
    "image" -> "[itemprop=image]")

}