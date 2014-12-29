package util

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

trait BaseCRUD_Unimplemented {

  val tableName = this.getClass.getSimpleName.toLowerCase.replaceAll("\\$", "")
  val clazz = this.getClass

  val s = {
    get[Long]("id")
  }

  def findAll = {
    val query = s"SELECT * FROM $tableName"
    val applyMethods = clazz.getDeclaredMethods.filter(m => m.getName.equals("apply"))
    println(applyMethods.map(_.getParameterTypes.mkString(",")).mkString(" "))
    val fields = {
      clazz.getDeclaredFields.map(field => field.getType)
    }
    println("Constructor" + clazz.getDeclaredConstructors)
    //val categories = DB.withConnection(implicit c => SQL(query)().map(row => Category(row[Int]("id"), row[String]("name"))).toList)
    //categories
  }

  def update(paramName: String, paramValue: String) = {
    val query = s"UPDATE $tableName SET $paramName = $paramValue"
    println(query)
  }

  def insert[T](entry: T): Option[Long] = {
    val fields = entry.getClass.getDeclaredFields.map(f => {
      val fName = f.getName
      fName -> entry.getClass.getDeclaredMethod(fName).invoke(entry)
    }).toMap
    val query = s"INSERT INTO $tableName (${fields.keys.mkString(",")}) VALUES (${fields.values.mkString(",")})"
    println(query)
    DB.withConnection(implicit c => SQL(query).executeInsert())
  }

  def delete(id: Int) = {
    val query = s"DELETE FROM $tableName WHERE id = $id"
    println(query)
  }
}
