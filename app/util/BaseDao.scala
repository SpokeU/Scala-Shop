package util

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

trait BaseDao {

  val tableName = this.getClass.getSimpleName.toLowerCase.replaceAll("\\$", "")

  def fieldExist[N,V](fieldName : N, fieldValue: V) = {
    DB.withConnection(implicit c =>
      SQL(s"SELECT COUNT(*) as count FROM $tableName WHERE $fieldName = '$fieldValue'")().head[Long]("count") > 0)
  }

}
