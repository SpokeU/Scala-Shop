package controllers

import model.Brand
import play.api.libs.json.Json
import play.api.mvc._

object BrandController extends Controller{

  implicit val brandJsonFormat = Json.format[Brand]

  def findAll() = Action{
    implicit request => {
      Ok(Json.toJson(Brand.findAll))
    }
  }
}
