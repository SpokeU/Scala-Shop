package controllers

import play.api.libs.json.JsValue
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.Controller
import play.api.mvc.Result
import play.api.libs.json.Reads
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError
import play.api.libs.json.Json
import play.api.libs.json.JsArray

trait CrudRestController extends Controller {

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
              case Nil    => action(model)
              case errors => BadRequest(Json.obj("errors" -> errors))
            }
          }
          case JsError(e) => BadRequest(s"Invalid JSON format $e")
        }
    }
  }

  def validateModel[T](model: T)(validators: (T => String)*) = {
    validators.foldLeft(List[String]())((acc, validator) => {
      val validationResult = validator(model)
      if (!validationResult.isEmpty()) validationResult :: acc else acc
    })
  }

  def all: Action[AnyContent]

  def create: Action[JsValue]

  def find(id: Long): Action[AnyContent]

  def update(id: Long): Action[JsValue]

  def delete(id: Long): Action[AnyContent]

}