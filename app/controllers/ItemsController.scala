package controllers

import actions.Actions.JsonModelAction
import model.Product
import model.Products
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc.Action
import play.api.mvc.AnyContent

object productsController extends CrudRestController {

  implicit val brandJsonFormat = Json.format[Product]

  def all: Action[AnyContent] = Action { Ok(Json.toJson(Products.all)) }

  def create: Action[JsValue] = JsonModelAction[Product] { implicit brand: Product =>
    Ok(Json.toJson(Products.create(brand)))
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(toJson(Products.delete(id)))
  }

  def find(id: Long): Action[AnyContent] = Action {
    Ok(toJson(Products.find(id)))
  }

  def update(id: Long): Action[JsValue] = JsonModelAction[Product] { implicit brand: Product =>
    Ok(toJson(Products.update(brand)))
  }

}
