package model

import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._

case class Characteristic(id: Option[Long], name: String, value: String)

class Characteristics(tag: Tag) extends Table[Characteristic](tag, "characteristics") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def value = column[String]("value", O.NotNull)

  def * = (id.?, name, value) <> (Characteristic.tupled, Characteristic.unapply)

}

object Characteristics {
  
  val db = play.api.db.slick.DB
  
  val characteristics = TableQuery[Characteristics]
  
}
