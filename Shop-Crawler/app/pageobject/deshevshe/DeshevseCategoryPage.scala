package pageobject.deshevshe

import scala.collection.JavaConversions.asScalaBuffer
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import config.DeshevseSettings.BASE_URL
import pageobject.CategoryPage
import pageobject.ProductInfoPage
import org.jsoup.nodes.Document
import webclient.WebClient

class DeshevseCategoryPage(page: => Element) extends CategoryPage {

  import DeshevseCategoryPage._

  def this(url: String) = {
    this(WebClient.getPage(url))
  }

  def name: String = {
    val categoryLevel = getCategoryLevel(page)
    page.select(nameSelectors(categoryLevel)).first.text
  }

  def products: Seq[ProductInfoPage] = {
    val categorylevel = getCategoryLevel(page)
    categorylevel match {
      case 3 => {
        val allProductsUrl = page.asInstanceOf[Document].baseUri() + "?perPage=2"
        val allProducts = WebClient.getPage(allProductsUrl)
        allProducts.select("a[class=name]").map { productLink =>
          new DeshevseProductInfoPage(BASE_URL + productLink.attr("href"))
        }
      }
      case _ => Nil
    }
  }

  def subCategories: Seq[CategoryPage] = {
    val categoryLevel = getCategoryLevel(page)
    subCategriesFunctions(categoryLevel)(page)
  }

  override def toString() = {
    page.html()
  }

}

object DeshevseCategoryPage {

  val nameSelectors = Map(
    1 -> ".m-title-text",
    2 -> ".catdrop",
    3 -> ".t-cr h1")

  val subCategriesFunctions = Map(
    1 -> getFirstLevelSubCategories,
    2 -> getSecondLevelSubCategories)

  def getFirstLevelSubCategories: (Element => Seq[CategoryPage]) = { page: Element =>
    page.select("a[class=catdrop]").map { categoryLink =>
      val subCategories = page.getElementById(categoryLink.attr("data-item"))
      val category = Jsoup.parse("").body().appendChild(categoryLink).appendChild(subCategories)
      DeshevseCategoryPage(category)
    }
  }

  def getSecondLevelSubCategories: (Element => Seq[CategoryPage]) = { page: Element =>
    page.select(".catnav-lvl a").map { categoryLink => DeshevseCategoryPage(BASE_URL + categoryLink.attr("href")) }
  }

  def getCategoryLevel(category: Element) = {
    category match {
      case cat if (cat.select(".t-cr").isEmpty() && !cat.select(".m-title-text").isEmpty()) => 1
      case cat if (!cat.select(".catdrop").isEmpty() && cat.select(".m-title-text").isEmpty()) => 2
      case cat if (!cat.select(".t-cr").isEmpty()) => 3
    }
  }

  def apply(page: Element) = new DeshevseCategoryPage(page)

  def apply(url: String) = new DeshevseCategoryPage(url)

}