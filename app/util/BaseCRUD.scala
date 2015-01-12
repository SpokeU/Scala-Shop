package util

import anorm._
import anorm.SqlParser._
import model.Brand
import play.api.db.DB
import play.api.Play.current
import scala.reflect.runtime.universe._
import scala.reflect.api._
import scala.reflect.api.StandardDefinitions
import model.Category

trait BaseCRUD_Unimplemented[T] {

  def t: TypeTag[T]

  val tableName = this.getClass.getSimpleName.toLowerCase.replaceAll("\\$", "")
  val clazz = this.getClass

  val parser = {
    get[Long]("id") ~
      get[String]("name")
  }

  def findAll = {
    val query = s"SELECT * FROM $tableName"
    getRowParser
    //val categories = DB.withConnection(implicit c => SQL(query)().map(row => Category(row[Int]("id"), row[String]("name"))).toList)
    //categories
  }

  def update(fieldName: String, fieldValue: String) = {
    val query = s"UPDATE $tableName SET $fieldName = $fieldValue"
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

  /*--------------Helpers---------------*/

  def getCaseAccessors = {
    implicit val typeTagMine = t
    typeOf.members.sorted.collect {
      case m: MethodSymbol if m.isCaseAccessor => m
    }
  }

  def getRowParser /*: RowParser[T]*/ = {
    val caseAccessors = getCaseAccessors;
    val rows = caseAccessors.map { rowParser(_) }
    rows.foldLeft(rows.head)((a, b) => a ~ b)
  }

  def rowParser(m: MethodSymbol): RowParser[Any] = {

    val stringType = typeOf[String]
    val intType = typeOf[scala.Int]
    val longType = typeOf[Long]
    val floatType = typeOf[Float]
    val doubleType = typeOf[Double]
    val booleanType = typeOf[Boolean]

    val name = m.name.toString();

    m.returnType match {
      case t if t =:= (stringType)  => str(name)
      case t if t =:= (intType)     => int(name)
      case t if t =:= (longType)    => long(name)
      case t if t =:= (floatType)   => float(name)
      case t if t =:= (doubleType)  => double(name)
      case t if t =:= (booleanType) => bool(name)
    }
  }
  
  def flattern(){
    
  }

}

/**
 * Just for testing purposes
 */
case class UserModel(val id: Long, val name: String, val password: Int)

object UserModel extends BaseCRUD_Unimplemented[UserModel] {

  def t: TypeTag[UserModel] = typeTag[UserModel]

}

object test {
  def main(args: Array[String]) {
    UserModel.findAll
  }
}

