package controllers

import model.Category
import play.api.mvc.Action
import play.api.mvc.Controller
import util.UserModel

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

}