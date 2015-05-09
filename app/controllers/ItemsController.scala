package controllers

import actions.Actions.JsonModelAction
import model.Item
import model.Items
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc.Action
import play.api.mvc.AnyContent

object ItemsController extends CrudRestController {

  implicit val brandJsonFormat = Json.format[Item]

  def all: Action[AnyContent] = Action { Ok(Json.toJson(Items.all)) }

  def create: Action[JsValue] = JsonModelAction[Item] { implicit brand: Item =>
    Ok(Json.toJson(Items.create(brand)))
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(toJson(Items.delete(id)))
  }

  def find(id: Long): Action[AnyContent] = Action {
    Ok(toJson(Items.find(id)))
  }

  def update(id: Long): Action[JsValue] = JsonModelAction[Item] { implicit brand: Item =>
    Ok(toJson(Items.update(brand)))
  }

}
