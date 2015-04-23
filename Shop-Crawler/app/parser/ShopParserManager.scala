package parser

import config._
import pageobject._
import pageobject.deshevshe._
import scala.collection.mutable._
import java.util.UUID

class ShopParserManager {

  val totalCategories = Map[UUID, Int]().withDefaultValue(0)
  val processedCategories = Map[UUID, Int]().withDefaultValue(0)

  val totalProducts = Map[UUID, Int]().withDefaultValue(0)
  val processedProducts = Map[UUID, Int]().withDefaultValue(0)

  def parseShop(shop: Shop): UUID = {
    implicit val jobId = UUID.randomUUID()
    val mainPage = getMainPage(shop)
    parseCategory(mainPage.categories.head)
    jobId
  }

  def parseCategory(category: CategoryPage)(implicit jobId: UUID): Unit = {
    totalCategories(jobId) += 1
    saveCategory(category)
    processedCategories(jobId) += 1
    category.products match {
      case Nil                            => category.subCategories.map { parseCategory(_) }
      case products: Seq[ProductInfoPage] => products.map { parseProduct(_) }
    }
  }

  def parseProduct(product: ProductInfoPage)(implicit jobId: UUID) = {
    totalProducts(jobId) += 1
    saveProduct(product)
    processedProducts(jobId) += 1
  }

  def jobDone(jobId: UUID) = {
    totalCategories(jobId) == processedCategories(jobId) &&
      totalProducts(jobId) == processedProducts(jobId)
  }

  def saveCategory(category: CategoryPage) = {
    println(s"Saving category ${category.name}")
  }

  def saveProduct(product: ProductInfoPage) = {
    println(s"Saving product ${product.name}")
  }

  def getMainPage(shop: Shop): MainPage = {
    shop match {
      case ROZETKA   => ???
      case DESHEVSHE => DeshevseMainPage(shop.url)
      case AMAZON    => ???
    }

  }
}

object ShopParserManager {
  def apply() = {
    new ShopParserManager()
  }
}