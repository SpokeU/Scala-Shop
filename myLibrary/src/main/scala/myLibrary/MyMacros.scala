package myLibrary

import scala.language.experimental.macros
//import scala.reflect.macros.contexts.Context
//import scala.reflect.macros.blackbox.Context
import scala.reflect.macros.whitebox.Context

trait Mappable[T] {

  def toMap(t: T): Map[Any, Any]
  def fromMap(map: Map[String, Any]): T 

}

object Mappable {
  implicit def materializeMappable[T]: Mappable[T] = macro materializeMappableImpl[T]

  def materializeMappableImpl[T: c.WeakTypeTag](c: Context): c.Expr[Mappable[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]

    val f = tpe.decls.collectFirst { case d: MethodSymbol if d.isPrimaryConstructor => d }.get.paramLists.head

    val companion = tpe.typeSymbol.companion

    val (toMapParams, fromMapParams) = f.map { field =>
      val name = field.name.toTermName
      val nameDecoded = name.decodedName.toString()
      val fieldType = field.typeSignature
      (q"$nameDecoded -> t.$name", q"map($nameDecoded).asInstanceOf[$fieldType]")
    }.unzip

    c.Expr[Mappable[T]] {
      q"""
      new Mappable[$tpe] {
        def toMap(t: $tpe) = Map(..$toMapParams)
        def fromMap(map: Map[String, Any]) = $companion(..$fromMapParams)
      }
    """
    }
  }

}

