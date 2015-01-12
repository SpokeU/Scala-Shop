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

  val tableName = this.getClass.getSimpleName.toLowerCase.replaceAll("\\$", "")

  def fieldExist[N, V](fieldName: N, fieldValue: V) = {
    DB.withConnection(implicit c =>
      SQL(s"SELECT COUNT(*) as count FROM $tableName WHERE $fieldName = '$fieldValue'")().head[Long]("count") > 0)
  }

  def create(entry : T): Option[Long]

  def findAll: List[T]

  def findById(id: Long): T

  def update(entry: T)

  def delete(id: Long) : Boolean
  
  /* ----- */
  
  def execute(x : Any) = {
    DB.withConnection(implicit c => x)
  }

}
