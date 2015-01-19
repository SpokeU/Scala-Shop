package controllers

import play.api.libs.json.JsValue
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller

trait CrudRestController extends Controller {

  def all: Action[AnyContent]

  def create: Action[JsValue]

  def find(id: Long): Action[AnyContent]

  def update(id: Long): Action[JsValue]

  def delete(id: Long): Action[AnyContent]

}