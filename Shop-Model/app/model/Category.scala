package model

import play.api.Play.current
import scala.slick.driver.MySQLDriver.simple._
import util.BaseDao

case class Category(id: Option[Long], name: String, image: String, parentId: Option[Long])
case class CategoryFull(id: Option[Long], name: String, image: String, parentId: Option[Long], subCategories: Seq[CategoryFull])

class Categories(tag: Tag) extends Table[Category](tag, "categories") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def image = column[String]("image", O.Default("img/defaultBrand.png"))

  def parentId = column[Long]("parent_id")

  def * = (id.?, name, image, parentId.?) <> (Category.tupled, Category.unapply)

}

object Categories {

  val db = play.api.db.slick.DB

  val categories = TableQuery[Categories]

  def create(entry: Category): Category = {
    db.withSession { implicit s =>
      {
        val id = categories.returning(categories.map { _.id }) += entry
        entry.copy(Some(id), entry.name, entry.image, entry.parentId)
      }
    }
  }

  def findById(id: Long): Category = {
    db.withSession { implicit s => categories.filter(_.id === id).first }
  }

  def findAll: Seq[Category] = {
    db.withSession { implicit s => categories.list }
  }

  def delete(id: Long): Boolean = {
    db.withSession { implicit s => categories.filter(_.id === id).delete > 0 }
  }

  def update(entry: Category): Boolean = {
    db.withSession { implicit s => categories.filter(_.id === entry.id).update(entry) > 0 }
  }

}