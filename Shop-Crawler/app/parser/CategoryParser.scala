package parser

import JobResultsAggregator._
import pageobject.CategoryPage
import pageobject.ProductInfoPage
import model.Categories
import model.Category

object CategoryParser {

  def parse(category: CategoryPage, parentCategoryId: Option[Long] = None)(implicit jobId: Int): Unit = {
    addToTotalCategories
    val categoryId = saveCategory(category, None)
    category.products match {
      case Nil                            => category.subCategories.map { parse(_, categoryId) }
      case products: Seq[ProductInfoPage] => products.map { ProductParser.parse(_) }
    }
    addToProcessedCategories
  }

  private def saveCategory(category: CategoryPage, parentId: Option[Long]) = {
    println(s"Saving category ${category.name}")
    val savedCategory = Categories.create(Category(None, category.name, "not implemented", parentId))
    println(s"Saved category $savedCategory")
    savedCategory.id
  }
}