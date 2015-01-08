package model

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db.DB

case class Brand(id: Long, name: String, imageUrl: String)

object Brand {

  val brandParser = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("image_link")
  } map {
    case id ~ name ~ imageLink => Brand(id, name, imageLink)
  }

  def findAll: List[Brand] = {
    DB.withConnection(implicit c => SQL("SELECT * FROM brand").as(brandParser *))
  }
}
