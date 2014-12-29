package service

import model.Category
import play.api.Logger


object CategoryService {

  def getCategories = {
    val allCategories = Category.findAll

    def getSubs(subCat: List[Category]): List[Category] = {
      if (subCat == Nil) {
        subCat
      } else {
        subCat.map { parent => {
          val subCategories = allCategories.filter {_.parentId.getOrElse(0) == parent.id}
          parent.copy(parent.id, parent.name, parent.imageLink, parent.parentId, getSubs(subCategories))
          }
        }
      }
    }
    getSubs(allCategories).filter(!_.parentId.isDefined)
  }

}
