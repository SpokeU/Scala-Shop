package controllers

import model.Category
import play.api.mvc.Action
import play.api.mvc.Controller
import util.UserModel
import myLibrary.Mappable
import myLibrary.Mappable._
import play.api.libs.json.Json
import play.api.mvc.AnyContent
import play.api.libs.json.JsValue

object Application extends CrudRestController {

  case class Person(name: String, age: Int)

  implicit val personParser = Json.format[Person]

  def ageValidator(p: Person) = if (p.age == 12) "Age could not be 12" else ""
  def nameValidator(p: Person) = if (p.name == "Vasya") "Name could not be Vasya" else ""

  def index = JsonModelAction[Person](ageValidator _, nameValidator _) { x: Person =>
    val mapper = implicitly[Mappable[Person]]
    println(mapper.toMap(x))
    Ok("")
  }

  def testMacro[T: Mappable](p: T) = {
    val mapper = implicitly[Mappable[T]]
    println(mapper.toMap(p))
    println("From map: " + mapper.fromMap(Map("name" -> "And", "age" -> 1)))
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