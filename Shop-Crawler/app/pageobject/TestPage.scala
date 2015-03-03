package pageobject

import org.jsoup.nodes.Element
import org.jsoup.Jsoup

class TestPage(page: => Element) {

  def getHeader = {
    println(page.select("row").first())
  }

}

object TestPage {
  def apply(url: String) = {
    lazy val page = Jsoup.connect(url).get
    new TestPage(page)
  }
}