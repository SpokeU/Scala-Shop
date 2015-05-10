package model

import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._
import util.CRUD

case class Brand(id: Option[Long], name: String, imageUrl: String)

class Brands(tag: Tag) extends Table[Brand](tag, "brands") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def imageUrl = column[String]("image", O.Default("imgdefaultBrand.png"))

  def * = (id.?, name, imageUrl) <> (Brand.tupled, Brand.unapply)

}

object Brands extends CRUD[Brand]{
  
  val db = play.api.db.slick.DB

  val brands = TableQuery[Brands]

  def all: List[Brand] = {
    db.withSession { implicit s => brands.list }
  }

  def create(entry: Brand): Brand = {
    db.withSession { implicit s =>
      {
        val id = brands.returning(brands.map { _.id }) += entry
        entry.copy(Some(id))
      }
    }
  }

  def delete(id: Long): Boolean = {
    db.withSession { implicit s => brands.filter(_.id === id).delete > 0 }
  }

  def find(id: Long): Brand = {
    db.withSession { implicit s => brands.filter(_.id === id).first }
  }

  def update(entry: Brand): Boolean = {
    db.withSession { implicit s => brands.filter(_.id === entry.id).update(entry) > 0 }
  }
}
