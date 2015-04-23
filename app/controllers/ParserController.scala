package controllers

import actions.Actions.JsonModelAction
import play.api.mvc.BodyParsers._
import play.api.mvc._
import play.api.libs.json.JsValue
import config.Shop
import play.api.libs.json.JsValue
import config.Shop
import scala.concurrent.Future
import play.api.libs.json.Json

case class ParseShopRequest(shop: String)
case class ParseCategoryRequest(shop: String, categoryName: String)
case class ParseProductRequest(url: String)
case class ParseByIdRequest(id: Long)

object ParserController extends Controller {

  implicit val shopRequestJsonFormatter = Json.format[ParseShopRequest]
  implicit val categoryRequestJsonFormatter = Json.format[ParseCategoryRequest]
  implicit val productRequestJsonFormatter = Json.format[ParseProductRequest]
  implicit val idRequestJsonFormatter = Json.format[ParseByIdRequest]

  def parseShop = JsonModelAction { implicit request: ParseShopRequest =>
    Ok("Success")
  }

  def parseCategory = JsonModelAction { implicit category: ParseCategoryRequest =>
    Ok("Success")
  }

  def parseProduct = JsonModelAction { implicit product: ParseProductRequest =>
    Ok("Success")
  }

  def parseCategoryById = JsonModelAction { implicit categoryId: ParseByIdRequest =>
    Ok("Success")
  }

  def parseProductById = JsonModelAction { implicit categoryId: ParseByIdRequest =>
    Ok("Success")
  }

}