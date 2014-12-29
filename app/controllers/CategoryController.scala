package controllers

import model.Category
import play.api.data.Form
import play.api.data.Forms.nonEmptyText
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import play.api.mvc.Request
import service.CategoryService
import util.FormInputNames
import play.api.Logger
import formbeans.CategoryForm
import validators.Validators

object CategoryController extends Controller {

  implicit val categoryFormat = Json.format[Category]

  val categoryForm = Form(
    mapping(
      FormInputNames.NAME -> nonEmptyText,
      FormInputNames.IMAGE_LINK -> text,
      FormInputNames.PARENT_ID -> longNumber)
      (CategoryForm.apply)(CategoryForm.unapply).verifying(Validators.uniqueCategoryName)
  )

  def all = Action { implicit request =>
    {
      val all = CategoryService.getCategories 
      Ok(views.html.admin.category.categories(all)(categoryForm))
    }
  }

  def add = Action {
    implicit request =>
      {
        categoryForm.bindFromRequest.fold(
          withErrors => Ok(views.html.admin.category.categories(CategoryService.getCategories)(withErrors)),
          categoryForm => {
            Category.add(categoryForm.name, categoryForm.imageLink, categoryForm.parentId)
            Redirect(routes.CategoryController.all)
          })
      }
  }

  def getParam(param: String)(implicit request: Request[AnyContent]) = {
    request.body.asFormUrlEncoded.get(param).flatMap(_.headOption)
  }

}
