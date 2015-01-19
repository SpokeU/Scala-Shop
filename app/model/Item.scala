package model

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.GetResult
import scala.slick.jdbc.{ StaticQuery => Q }
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.lifted.ForeignKeyQuery

import play.api.Play.current
import util.BaseDao

case class Item(id: Option[Long], name: String, price: BigDecimal, description: String, brandId: Long, categoryId: Long)

case class ItemFetched(id: Option[Long], name: String, price: BigDecimal, description: String, brand: Brand, category: Category, images: List[String])

class Items(tag: Tag) extends Table[Item](tag, "items") {

  def id = column[Long]("id", O.PrimaryKey, O.NotNull, O.AutoInc)

  def name = column[String]("name", O.NotNull)

  def price = column[BigDecimal]("price", O.Nullable)

  def description = column[String]("description", O.Nullable)

  def brandId = column[Long]("brand_id")

  def categoryId = column[Long]("category_id")

  def * = (id.?, name, price, description, brandId, categoryId) <> (Item.tupled, Item.unapply)

  def brand: ForeignKeyQuery[Brands, Brand] = {
    foreignKey("brand_id", brandId, TableQuery[Brands])(_.id)
  }
}

object Items extends BaseDao[Item] {

  implicit val itemResult = GetResult(r => Item(r.nextLongOption(), r.<<, r.<<, r.<<, r.<<, r.<<))

  val db = play.api.db.slick.DB

  val items = TableQuery[Items]

  def all: List[Item] = {
    db.withSession { implicit s => Q.queryNA[Item]("SELECT * FROM items").list }
  }

  def create(entry: Item): Option[Long] = {
    db.withSession { implicit s => Some(items += entry) }
  }

  def delete(id: Long): Boolean = {
    db.withSession { implicit s => items.filter(_.id === id).delete > 0 }
  }

  def find(id: Long): Item = {
    db.withSession { implicit s => items.filter(_.id === id).first }
  }

  def update(entry: Item): Boolean = {
    db.withSession { implicit s => items.filter(_.id === entry.id).update(entry) > 0 }
  }

  /* Using string interpolation */
  def allFetched = {
    val sql = """SELECT item.id as item_id, 
                    item.name as item_name, 
                    item.price, item.description, 
                      brand.id as brand_id,
                    brand.name as brand_name, 
                    brand.image_link as brand_image,
                      category.id as category_id,
                      category.parent_id as category_parent_id,
                      category.name as category_name,
                      category.image_link as category_image 
                  FROM items item 
                  INNER JOIN brands brand 
                  ON item.brand_id = brand.id
                  INNER JOIN categories category
                  ON category.id = item.category_id
      """
    implicit val itemWithBrandResult = GetResult(r => ItemFetched(r.<<, r.<<, r.<<, r.<<, Brand(r.<<, r.<<, r.<<), Category(r.<<, r.<<, r.<<, r.<<), Nil))
    db.withSession { implicit s => Q.queryNA[ItemFetched](sql).list }
  }

  def joinTest = {
    val joinQ = items join Brands.brands on (_.brandId === _.id) filter { case (i, b) => b.name === "HP" }
    val res = joinQ.mapResult { case (item, brand) => Item(item.id, item.name, item.price, item.description, item.brandId, item.categoryId) }

    db.withSession { implicit s => println(res.list) }

    val joinQuery = for {
      i <- items
      b <- i.brand
    } yield ((i.id.?, i.name, i.price, i.description, i.brandId, i.categoryId) <> (Item.tupled, Item.unapply))
    db.withSession { implicit s => println(joinQuery.list) }
  }

  def interpolationTest = {
    import scala.slick.jdbc.StaticQuery.interpolation
    val name = "HP-Compaq CQ-538745"
    val allItems = sql"SELECT * FROM items WHERE name = $name".as[Item]
    val result = db.withSession { implicit s => allItems.list }
    println(result)
  }

}

