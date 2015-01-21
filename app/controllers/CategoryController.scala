package controllers

import model.Category
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import play.api.mvc.Request
import service.CategoryService
import play.api.libs.json.JsValue

object CategoryController extends CrudRestController {

  implicit val categoryFormat = Json.format[Category]

  def all = Action { implicit request =>
    {
      val all = CategoryService.getCategories
      Ok("Ok")
    }
  }

  def create: Action[JsValue] = {
    ???
  }

  def delete(id: Long): Action[AnyContent] = {
    ???
  }

  def find(id: Long): Action[AnyContent] = {
    ???
  }

  def update(id: Long): Action[JsValue] = {
    ???
  }

  def getParam(param: String)(implicit request: Request[AnyContent]) = {
    request.body.asFormUrlEncoded.get(param).flatMap(_.headOption)
  }

}
