package validators

import formbeans.CategoryForm
import model.Category
import play.api.data.validation._

object Validators {

  val uniqueCategoryName: Constraint[CategoryForm] = Constraint("constraints.category_name")({
    categoryForm =>
      if ( /*Category.fieldExist("name", categoryForm.name)*/ true) {
        Invalid(Seq(ValidationError("Name already exists")))
      } else {
        Valid
      }
  })

}
