package model

import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._
import util.CRUD

case class Category(id: Option[Long], name: String, image: Option[String], parentId: Option[Long])
case class CategoryFull(id: Option[Long], name: String, image: Option[String], parentId: Option[Long], subCategories: Seq[CategoryFull])

class Categories(tag: Tag) extends Table[Category](tag, "categories") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def image = column[String]("image", O.Default("img/defaultBrand.png"))

  def parentId = column[Long]("parent_id")

  def * = (id.?, name, image.?, parentId.?) <> (Category.tupled, Category.unapply)

}

object Categories extends CRUD[Category]{

  val db = play.api.db.slick.DB

  val categories = TableQuery[Categories]

  def all: List[Category] = {
    db.withSession { implicit s => categories.list }
  }

  def create(entry: Category): Category = {
    db.withSession { implicit s =>
      {
        val id = categories.returning(categories.map { _.id }) += entry
        entry.copy(Some(id))
      }
    }
  }

  def find(id: Long): Category = {
    db.withSession { implicit s => categories.filter(_.id === id).first }
  }

  def delete(id: Long): Boolean = {
    db.withSession { implicit s => categories.filter(_.id === id).delete > 0 }
  }

  def update(entry: Category): Boolean = {
    db.withSession { implicit s => categories.filter(_.id === entry.id).update(entry) > 0 }
  }

}
