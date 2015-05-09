package actions

import play.api.libs.json.Json.fromJson
import play.api.mvc._
import play.api.mvc.Request
import play.api.mvc.Result
import play.api.libs.json._
import play.api.mvc.BodyParsers.parse
import play.api.mvc.Results
import play.api.mvc.Action
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.AnyContent
import play.api.mvc.Results._

object Actions {

  def JsonModelAction[T](action: T => Result)(implicit parser: Reads[T]): Action[JsValue] = {
    JsonModelAction[T]()(action)
  }

  def JsonModelAction[T](validators: (T => String)*)(action: T => Result)(implicit parser: Reads[T]): Action[JsValue] = {
    Action(parse.json) {
      implicit request =>
        Json.fromJson(request.body) match {
          case JsSuccess(model, _) => {
            val errors = validateModel(model)(validators: _*)
            errors match {
              case Nil => {
                try {
                  action(model)
                } catch {
                  case e: Exception => 
                    e.printStackTrace()
                    BadRequest(Json.obj("errors" -> e.getMessage))
                }
              }
              case validationErrors => BadRequest(Json.obj("errors" -> validationErrors))
            }
          }
          case JsError(e) => BadRequest(Json.obj("errors" -> s"Invalid JSON format $e"))
        }
    }
  }

  def validateModel[T](model: T)(validators: (T => String)*) = {
    validators.foldLeft(List[String]())((acc, validator) => {
      val validationResult = validator(model)
      if (!validationResult.isEmpty()) validationResult :: acc else acc
    })
  }

}