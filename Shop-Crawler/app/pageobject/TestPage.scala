package pageobject

import org.jsoup.nodes.Element
import org.jsoup.Jsoup

class TestPage(page: => Element) {

  protected def this(url: String) = {
    this(Jsoup.connect(url).get)
  }

  protected def this(url: String, loadFunction: String => Element) = {
    this(loadFunction(url))
  }

  def getHeader = {
    println(page.select("row").first())
  }

}

object TestPage {
  def apply(url: String) = {
    new TestPage(url, { Jsoup.connect(_).get })
  }
}

/*-------*/

trait LazyPageTest {
  lazy val page: Element = loadPage(source)

  def loadPage(source: Any) = {
    source match {
      case url: String   => loadPageFromUrl(url)
      case elem: Element => loadPageFromElement(elem)
      case source: Any   => loadPageFromSource(source)
    }
  }

  def loadPageFromUrl(url: String) = {
    println("Connect")
    Jsoup.connect(url).get
  }

  def loadPageFromElement(elem: Element) = {
    elem
  }

  def loadPageFromSource(source: Any): Element = {
    Jsoup.parse("")
  }

  val source: Any
}

class TestLazyPage(val source: Any) extends LazyPageTest {
  def getHeader = {
    println(page.select("row").first())
  }
}

