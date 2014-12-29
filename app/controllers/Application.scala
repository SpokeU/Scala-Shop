package controllers

import model.Category
import play.api.mvc.Action
import play.api.mvc.Controller

object Application extends Controller {

  def index = Action { implicit request => {
    println(Category.fieldExist("id", 10))
    Ok(views.html.index(request))
  }
  }


}