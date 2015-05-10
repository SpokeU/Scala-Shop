package model

import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._
import util.CRUD

case class Characteristic(id: Option[Long], name: String, value: String)

class Characteristics(tag: Tag) extends Table[Characteristic](tag, "characteristics") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def value = column[String]("value", O.NotNull)

  def * = (id.?, name, value) <> (Characteristic.tupled, Characteristic.unapply)

}

object Characteristics extends CRUD[Characteristic]{

  val db = play.api.db.slick.DB

  val characteristics = TableQuery[Characteristics]

  def all: List[Characteristic] = {
    db.withSession { implicit s => characteristics.list }
  }

  def create(entry: Characteristic): Characteristic = {
    db.withSession { implicit s =>
      {
        val id = characteristics.returning(characteristics.map { _.id }) += entry
        entry.copy(Some(id))
      }
    }
  }

  def find(id: Long): Characteristic = {
    db.withSession { implicit s => characteristics.filter(_.id === id).first }
  }

  def delete(id: Long): Boolean = {
    db.withSession { implicit s => characteristics.filter(_.id === id).delete > 0 }
  }

  def update(entry: Characteristic): Boolean = {
    db.withSession { implicit s => characteristics.filter(_.id === entry.id).update(entry) > 0 }
  }

}
