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
import myLibrary.TestCrawler
import play.api.Logger

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