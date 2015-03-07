package pageobject.deshevshe

import scala.collection.JavaConversions._

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

import pageobject.CategoryPage
import pageobject.MainPage
import webclient.WebClient

class DeshevseMainPage(val source: String) extends MainPage {

  def categories: Seq[CategoryPage] = {
    page.select(".actionmenu").map { new DeshevseCategoryPage(_) }
  }

  def logo: String = {
    page.select(".logo img").attr("src")
  }

  override def loadPageFromUrl(url: String): Element = {
    val driver = WebClient.phantomJS
    driver.get(url)
    driver.findElementsByCssSelector(".actionmenu").map { _.click() }
    Thread.sleep(2000) //TODO: wait for ajax with waiter
    Jsoup.parse(driver.getPageSource)
  }

}

object DeshevseMainPage {

  def apply(url: String) = {
    new DeshevseMainPage(url)
  }

}