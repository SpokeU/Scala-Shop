package util

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db.DB

trait BaseDao[T] {

  def all: List[T]

  def create(entry: T): Option[T]

  def find(id: Long): T

  def update(entry: T): Boolean

  def delete(id: Long): Boolean

}
