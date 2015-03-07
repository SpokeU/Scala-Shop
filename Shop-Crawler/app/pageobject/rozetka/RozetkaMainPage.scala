package pageobject.rozetka

import org.jsoup.Jsoup
import pageobject.ProductInfoPage
import pageobject.MainPage
import pageobject.CategoryPage
import org.jsoup.nodes.Element
import scala.collection.JavaConversions._

class RozetkaMainPage(val source: String) extends MainPage {

  def categories: Seq[CategoryPage] = {
    page.select(".m-main-t-i").map(RozetkaCategoryPage(_))
  }

  def logo: String = ""

}