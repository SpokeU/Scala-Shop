package pageobject.rozetka

import org.jsoup.Jsoup
import pageobject.ProductInfoPage
import pageobject.MainPage
import pageobject.CategoryPage
import org.jsoup.nodes.Element
import scala.collection.JavaConversions._

class RozetkaMainPage(page: Element) extends MainPage {

  def this(url: String) = {
    this(Jsoup.connect(url).get)
  }

  def categories: Seq[CategoryPage] = {
    page.select(".m-main-t-i").map(RozetkaCategoryPage(_))
  }

  def logo: String = ""

}