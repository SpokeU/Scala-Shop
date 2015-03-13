package pageobject.rozetka

import pageobject.CategoryPage
import pageobject.ProductInfoPage
import org.jsoup.nodes.Element
import scala.collection.JavaConversions._
import org.jsoup.Jsoup
import scala.reflect.internal.util.StringOps

/**
 * @author AlexM
 */
class RozetkaCategoryPage(val source: Any) extends CategoryPage {

  def this(url: String) = {
    this(Jsoup.connect(url).timeout(10*1000).get)
  }

  def name: String = {
    ???
  }

  def products: List[ProductInfoPage] = {
    Nil
  }

  def subCategories: Seq[CategoryPage] = {
    val rows = page.select("[name=drop-elem] li[class=x1]")

    rows.map { row =>
      row.children.collect {
        case x if (x.tagName().equals("h6") && 
            ((x.previousElementSibling() == null && x.nextElementSibling() == null) ||
            x.previousElementSibling() != null && x.previousElementSibling().tagName().equals("br"))) => {
          println("Processing - " + x.text)
          RozetkaCategoryPage(x.select(".m-main-fat-link2").first().attr("href"))
        }
        
        case x if (x.tagName().equals("h6") && x.nextElementSibling().tagName().equals("ul")) => {
          println("Processing - " + x.select("h6").first.text)
          val result = x.appendElement(x.nextElementSibling().html())
          RozetkaCategoryPage(result)
        }
      }
    }.flatten
  }

  private def getCategoryLevel(element: Element) = {
    element.select(".m-main-t-i").isEmpty() //1

  }

}

object RozetkaCategoryPage {

  val nameSelectors = Map(
    1 -> ".m-main-title-text",
    2 -> ".m-main-fat-link2",
    3 -> ".title-page h1",
    4 -> ".title-page-with-filters h1")

  def apply(element: Element) = new RozetkaCategoryPage(element)

  def apply(element: String) = new RozetkaCategoryPage(element)

}