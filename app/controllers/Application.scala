package controllers

import actions.Actions.JsonModelAction
import myLibrary.Mappable
import myLibrary.Mappable.materializeMappable
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import validators.Validators._
import model.Items

case class Person(name: String, age: Int)

object Application extends Controller {

  implicit val personParser = Json.format[Person]

  def index = Action {
    Ok(views.html.index())
  }

  def testPost = JsonModelAction[Person](ageValidator _, nameValidator _) { x: Person =>
    val mapper = implicitly[Mappable[Person]]
    println(mapper.toMap(x))
    Ok("")
  }

  def testGet = Action {
    Items.joinTest
    Ok("Success")
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

}