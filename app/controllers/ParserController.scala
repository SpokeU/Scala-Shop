package controllers

import actions.Actions.JsonModelAction
import parser.SimpleParserManager
import play.api.libs.json.Json
import play.api.mvc.Controller
import actors.JobResultAggregator
import parser.JobResultsAggregator
import play.api.mvc.Action
import config.Shop
import config.DESHEVSHE
import config.ROZETKA

case class ParseShopRequest(shop: String)
case class ParseCategoryRequest(shop: String, categoryName: String)
case class ParseProductRequest(url: String)
case class IdRequest(id: Int)

case class StatusResponse(active: Boolean, status: Option[String])

object ParserController extends Controller {

  implicit val shopRequestJsonFormatter = Json.format[ParseShopRequest]
  implicit val categoryRequestJsonFormatter = Json.format[ParseCategoryRequest]
  implicit val productRequestJsonFormatter = Json.format[ParseProductRequest]
  implicit val idRequestJsonFormatter = Json.format[IdRequest]

  implicit val statusResponseJsonFormatter = Json.format[StatusResponse]

  def parseShop = JsonModelAction { implicit request: ParseShopRequest =>
    val jobId = SimpleParserManager.parseShop(request.shop)
    Ok(s"Started job with id jobId $jobId")
  }

  def parseCategory = JsonModelAction { implicit category: ParseCategoryRequest =>
    Ok("Success")
  }

  def parseProduct = JsonModelAction { implicit product: ParseProductRequest =>
    Ok("Success")
  }

  def parseCategoryById = JsonModelAction { implicit categoryId: IdRequest =>
    Ok("Success")
  }

  def parseProductById = JsonModelAction { implicit categoryId: IdRequest =>
    Ok("Success")
  }

  def getShopStatus(shop: String) = Action { implicit request =>
    Ok(Json.toJson(getShopStatusResponse(shop)))
  }

  def getCategoryStatus(categoryId: String) = Action { implicit request =>
    Ok("Success")
  }

  def getProductsStatus(categoryId: String) = Action { implicit request =>
    Ok("Success")
  }

  /* -------------- */

  def getShopsWithParseStatus = Action {
    val response = Shop.getAllShops.map { shop => (shop.name -> getShopStatusResponse(shop.name)) }
    Ok(Json.toJson(response.toMap))
  }

  def getAllShops = Action {
    Ok(Json.toJson(Shop.getAllShops.map(_.name)))
  }

  /* ---- PRIVATE METHODS ---- */

  private def getShopStatusResponse(shop: String) = {
    val isActive = SimpleParserManager.isShopParsingAcive(shop)
    val status = if (isActive) Some(SimpleParserManager.getShopStatus(shop)) else None
    StatusResponse(isActive, status)
  }

}