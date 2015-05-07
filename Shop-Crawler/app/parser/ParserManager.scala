package parser

import config.Shop
import pageobject.CategoryPage
import pageobject.ProductInfoPage

trait ParserManager {

  def parseShop(shop: String): Unit

  def parseCategory(categoryId: Int): Unit

  def parseProduct(productId: Int): Unit

  /**
   * Returns parsing status of shop. Whether its active or not
   */
  def isShopParsingAcive(shop: String): Boolean

  /**
   * Returns parsing status of category. Whether its active or not
   */
  def isCategoryParsingActive(categoryId: Int): Boolean

  /**
   * Returns parsing status of product. Whether its active or not
   */
  def isProductParsingActive(productId: Int): Boolean

}