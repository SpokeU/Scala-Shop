package controllers

import actions.Actions.JsonModelAction
import play.api.libs.json.JsValue
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import validators.Validators._

object Application extends Controller {

  def index = Action {
    println("GOT REQUEST")
    Ok(views.html.index())
  }

  def testGet = Action {
    println("GOT REQUEST")
    Map("Govno" -> 1)
    Ok("Success")
  }
}