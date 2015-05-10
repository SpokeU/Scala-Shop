package util

import play.api.Play.current
import scala.slick.lifted.TableQuery
import scala.slick.lifted.AbstractTable
import scala.slick.driver.PostgresDriver.simple._

trait CRUD[T] {

  //val db = play.api.db.slick.DB

  //def query: TableQuery[_ <: Table[T]]

  def all: List[T]

  def create(entry: T): T

  def delete(id: Long): Boolean

  def find(id: Long): T

  def update(entry: T): Boolean

}
