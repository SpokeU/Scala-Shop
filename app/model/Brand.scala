package model

import play.api.Play.current
import scala.slick.driver.MySQLDriver.simple._
import util.BaseDao

case class Brand(id: Option[Long], name: String, imageUrl: String)

class Brands(tag: Tag) extends Table[Brand](tag, "brands") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def imageUrl = column[String]("image_link", O.Default("img/defaultBrand.png"))

  def * = (id.?, name, imageUrl) <> (Brand.tupled, Brand.unapply)

}

object Brands extends BaseDao[Brand] {

  val db = play.api.db.slick.DB

  val brands = TableQuery[Brands]

  def all: List[Brand] = {
    db.withSession { implicit s => brands.list }
  }

  def create(entry: Brand): Option[Long] = {
    db.withSession { implicit s => Some(brands += entry) }
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
