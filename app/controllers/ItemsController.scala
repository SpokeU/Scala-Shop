package controllers

import play.api.mvc.Action
import play.api.libs.json.JsValue
import play.api.mvc.AnyContent

object ItemsController extends CrudRestController {
  
  def all: Action[AnyContent] = {
    ???
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

}
