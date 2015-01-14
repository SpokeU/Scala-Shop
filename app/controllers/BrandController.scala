package controllers

import scala.annotation.implicitNotFound
import model.Brand
import model.Brands
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import play.api.data.Form
import play.api.data.Forms._

object BrandController extends CrudRestController {

  implicit val brandJsonFormat = Json.format[Brand]

  def all: Action[AnyContent] = {
    def f = action1 _ compose action2 _
    f("a")
    action4("",1)
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
    Ok(toJson(Brands.update(brand)))
  }

  def action1(action: String) = {
    println(action + " From a1")
    action + "modified1"
  }

  def action2(action: String) = {
    println(action + " From a2")
    action
  }

  def action3(act: String, times: Int) = {
    0 to times map {
      println(_)
    }
  }

  def action4(act: String, times: Int) = {
    

    println(List("err:1", "err:2", "err:3").fold("")((a, b) => a + b))
  }

}
