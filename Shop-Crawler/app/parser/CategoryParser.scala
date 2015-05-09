package parser

import JobResultsAggregator._
import pageobject.CategoryPage
import pageobject.ProductInfoPage
import model.Categories
import model.Category
import play.api.Logger

object CategoryParser {

  def parse(category: CategoryPage, parentCategoryId: Option[Long] = None)(implicit jobId: Int): Unit = {
    addToTotalCategories
    val categoryId = saveCategory(category, parentCategoryId)
    category.products match {
      case Nil                            => category.subCategories.map { parse(_, categoryId) }
      case products: Seq[ProductInfoPage] => products.map { ProductParser.parse(_) }
    }
    addToProcessedCategories
  }

  private def saveCategory(category: CategoryPage, parentId: Option[Long]) = {
    Logger.info(s"Saving category ${category.name}")
    Categories.create(Category(None, category.name, None, parentId)).id
  }
}