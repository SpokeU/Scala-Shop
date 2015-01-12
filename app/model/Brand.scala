package model

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db.DB
import util.BaseDao

case class Brand(id: Long, name: String, imageUrl: String)

object Brand extends BaseDao[Brand] {

  val brandParser = {
    long("id") ~
      str("name") ~
      str("image_link")
  }.map(flatten).map(tuple => Brand.apply _ tupled(tuple))

  def findAll: List[Brand] = {
    DB.withConnection(implicit c => SQL("SELECT * FROM brand").as(brandParser *))
  }

  def create(entry: Brand): Option[Long] = {
    DB.withConnection { implicit c =>
      SQL("INSERT INTO brand (name, image_link) VALUES ({name} , {image_link})").
        on("name" -> entry.name, "image_link" -> entry.imageUrl).executeInsert()
    }
  }

  def delete(id: Long): Boolean = {
    val numRows = DB.withConnection { implicit c => SQL("DELETE FROM brand WHERE id = {id}").on("id" -> id).executeUpdate() }
    numRows == 0
  }

  def update(entry: Brand): Unit = {
    ???
  }

  def findById(id: Long): Brand = {
    ???
  }
}
