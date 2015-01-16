package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import util.FormInputNames

/**
 * Created by Silvester on 12/21/2014.
 */
object ItemsController extends Controller {

  /*val itemForm = Form(mapping(
    FormInputNames.NAME -> nonEmptyText,
    FormInputNames.DESCRIPTION -> text,
    FormInputNames.PRICE -> bigDecimal,
    FormInputNames.IMAGE_LINK -> text,
    FormInputNames.BRAND -> text
  )(NewItemRequest.apply)(NewItemRequest.unapply))
  */
  def getAll = Action { implicit request => {
      Ok("Ok")
    }
  }

  def getByCategory(category: String) = Action {
    Ok
  }

  /**
   * Filter
   * @return
   */
  def getByCriteria = {
    implicit request: Request[AnyContent] => {
      Ok
    }
  }

  def add = Action {
    Ok
  }

}
