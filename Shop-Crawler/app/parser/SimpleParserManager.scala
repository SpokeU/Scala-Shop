package parser

import scala.collection.mutable.Map
import scala.collection.mutable.Seq
import config.AMAZON
import config.DESHEVSHE
import config.ROZETKA
import config.Shop
import pageobject.CategoryPage
import pageobject.MainPage
import pageobject.ProductInfoPage
import pageobject.deshevshe.DeshevseMainPage
import JobResultsAggregator._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object SimpleParserManager extends ParserManager {

  val jobActive = Map[Int, Boolean]().withDefaultValue(false)

  def parseShop(shop: String) = {
    implicit val jobId = getShopRequestId(shop)
    Shop.fromString(shop) match {
      case Some(shop) => if (!jobActive(jobId)) {
        startJob
        val job = Future { ShopParser.parseShop(shop) }
        job.map { x => finishJob }
      } else { throw new Exception(s"Job $jobId already running") }
      case None => throw new Exception(s"There is no shop with name $shop")
    }
  }

  def parseCategory(categoryId: Int): Unit = {
    ???
  }

  def parseProduct(productId: Int): Unit = {
    ???
  }

  private def startJob(implicit jobId: Int) = {
    jobActive(jobId) = true
  }

  private def finishJob(implicit jobId: Int) = {
    jobActive(jobId) = false
  }

  /* JOB STATUSES */

  def isShopParsingAcive(shop: String) = {
    isJobActive(getShopRequestId(shop))
  }

  def isCategoryParsingActive(categoryId: Int) = {
    isJobActive(getCategoryRequestId(categoryId))
  }

  def isProductParsingActive(productId: Int) = {
    isJobActive(getProductRequestId(productId))
  }

  def getShopStatus(shop: String) = {
    implicit val jobId = getShopRequestId(shop)
    "Categories - " + JobResultsAggregator.getTotalCategories + "/" + JobResultsAggregator.getProcessedCategories +
      "Products - " + JobResultsAggregator.getTotalProducts + "/" + JobResultsAggregator.getProcessedProducts
  }

  def getCategoryStatus(categoryId: Int) = {

  }

  def getProductStatus(productId: Int) = {

  }

  private def isJobActive(implicit jobId: Int): Boolean = {
    jobActive(jobId)
  }

  private def getShopRequestId(shop: String) = {
    s"SHOP-${shop.toUpperCase}".hashCode
  }

  private def getCategoryRequestId(categoryId: Int) = {
    s"CATEGORY-${categoryId}".hashCode
  }

  private def getProductRequestId(productId: Int) = {
    s"PRODUCT-${productId}".hashCode
  }
}