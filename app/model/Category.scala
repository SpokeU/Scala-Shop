package model

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import util.BaseDao

case class Category(id: Long = -1, name: String, imageLink: String, parentId: Option[Long], subCategories: List[Category] = Nil)

object Category {

  val categoryParser = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("image_link") ~
      get[Option[Long]]("parent_id") map {
        case id ~ name ~ imageLink ~ parentId => Category(id, name, imageLink, parentId)
      }
  }

  def findAll: List[Category] = DB.withConnection(implicit c => SQL("SELECT * FROM category").as(categoryParser *))

  def add(name: String, imageLink: String = "default", parentId: Long) = DB.withConnection(implicit c =>
    SQL("INSERT INTO category (name, image_link, parent_id) VALUES ({name}, {image_link}, {parent_id})").
      on("name" -> name, "image_link" -> imageLink, "parent_id" -> parentId).executeInsert())

  def create(entry: Category): Option[Long] = {
    ???
  }

  def delete(id: Long): Boolean = {
    ???
  }

  def findById(id: Long): Category = {
    ???
  }

  def update(entry: Category): Unit = {
    ???
  }

}
