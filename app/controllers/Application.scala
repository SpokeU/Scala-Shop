package controllers

import model.Category
import play.api.mvc.Action
import play.api.mvc.Controller
import util.UserModel
import myLibrary.Mappable
import myLibrary.Mappable._

object Application extends Controller {

  case class Person(name: String, age: Int)

  def index = Action {
    def f = action1 _ compose action2 _
    f("a")
    action4("", 1)
    testMacro(Person("name", 1))
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

}