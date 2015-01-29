package controllers

import actions.Actions.JsonModelAction
import model.Brand
import model.Brands
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc.Action
import play.api.mvc.AnyContent

object BrandController extends CrudRestController {

  implicit val brandJsonFormat = Json.format[Brand]

  def all: Action[AnyContent] = {
    Action { Ok(Json.toJson(Brands.all)) }
  }

  def create: Action[JsValue] = JsonModelAction[Brand] { implicit brand: Brand =>
    Ok(Json.toJson(Brands.create(brand)))
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(toJson(Brands.delete(id)))
  }

  def find(id: Long): Action[AnyContent] = Action {
    Ok(toJson(Brands.find(id)))
  }

  def update(id: Long): Action[JsValue] = JsonModelAction[Brand] { implicit brand: Brand =>
    println(brand)
    Ok(toJson(Brands.update(brand)))
  }

}
