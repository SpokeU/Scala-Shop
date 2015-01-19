package controllers

import model.Category
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import play.api.mvc.Request
import service.CategoryService

object CategoryController extends Controller {

  implicit val categoryFormat = Json.format[Category]

  def all = Action { implicit request =>
    {
      val all = CategoryService.getCategories
      Ok("Ok")
    }
  }

  def add = Action {
    implicit request =>
      Ok("Ok")
  }

  def getParam(param: String)(implicit request: Request[AnyContent]) = {
    request.body.asFormUrlEncoded.get(param).flatMap(_.headOption)
  }

}
