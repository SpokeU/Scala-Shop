package service

import model.Category
import play.api.Logger
import model.Categories
import model.CategoryFull

object CategoryService {

  def getCategories = {
    val allCategories = Categories.all

    def getSubs(subCat: Seq[CategoryFull]): Seq[CategoryFull] = {
      if (subCat == Nil) {
        subCat
      } else {
        subCat.map { parent =>
          {
            val subCategories = allCategories.filter { _.parentId.getOrElse(0) == parent.id }.map { toFull(_) }
            parent.copy(parent.id, parent.name, parent.image, parent.parentId, getSubs(subCategories))
          }
        }
      }
    }
    getSubs(allCategories.map { toFull(_) }).filter(!_.parentId.isDefined)
  }

  def toFull(category: Category) = {
    CategoryFull(category.id, category.name, category.image, category.parentId, Nil)
  }

}
