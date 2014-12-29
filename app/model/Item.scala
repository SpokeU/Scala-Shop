package model

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class Item(id: Long, name: String, price: BigDecimal, description: String, brand: String)

object Item {

  val itemParser = {
    get[Long]("id") ~
    get[String]("name") ~
    get[BigDecimal]("price") ~
    get[String]("description") ~
    get[String]("brand")
  }

  def findAll = DB.withConnection(implicit c => {
    SQL("SELECT * FROM item")
  })

}
