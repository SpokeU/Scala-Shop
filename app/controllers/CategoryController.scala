package controllers

import model.Categories
import model.Category
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Request

object CategoryController extends CrudRestController {

  implicit val categoryFormat = Json.format[Category]

  def all: Action[AnyContent] = Action { Ok(Json.toJson(Categories.all)) }

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
