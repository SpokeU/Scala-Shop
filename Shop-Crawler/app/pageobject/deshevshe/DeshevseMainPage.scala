package pageobject.deshevshe

import pageobject.MainPage
import pageobject.CategoryPage
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scala.collection.JavaConversions._
import webclient.WebClient
import webclient.WebClientType
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.html.HtmlElement
import org.openqa.selenium.interactions.Actions
import play.api.Logger

class DeshevseMainPage(page: Element) extends MainPage {

  def categories: Seq[CategoryPage] = {
    page.select(".actionmenu").map { new DeshevseCategoryPage(_) }
  }

  def logo: String = {
    page.select(".logo img").attr("src")
  }

}

object DeshevseMainPage {

  def apply(url: String) = {
    new DeshevseMainPage(loadPage(url))
  }

  def loadPage(url: String): Element = {
    val driver = WebClient.phantomJS
    driver.get(url)
    driver.findElementsByCssSelector(".actionmenu").map { _.click() }
    Thread.sleep(2000)//TODO: wait for ajax with waiter
    Jsoup.parse(driver.getPageSource)
  }
}