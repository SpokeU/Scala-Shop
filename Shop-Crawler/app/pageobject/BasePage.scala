package pageobject

import org.jsoup.nodes.Element
import org.jsoup.Jsoup

class BasePage(protected var page: Element) {

  protected def this(url: String) = {
    this(Jsoup.connect(url).get)
  }
  
}