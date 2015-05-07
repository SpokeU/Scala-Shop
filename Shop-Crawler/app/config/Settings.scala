package config

case class Setting(key: String, value: String) {
  override def toString() = value
}

object DeshevseSettings {

  val BASE_URL = Setting("deshevse_base_url", "http://deshevshe.net.ua/")

}

class Shop(val name: String, val url: String)
object Shop {
  def fromString(s: String): Option[Shop] = { getAllShops.find { _.toString == s } }
  def getAllShops = Seq(ROZETKA, DESHEVSHE, AMAZON)
}
case object ROZETKA extends Shop("Rozetka", "http://rozetka.com.ua/")
case object DESHEVSHE extends Shop("Deshevshe", "http://deshevshe.ua/")
case object AMAZON extends Shop("Amazon", "http://www.amazon.com/")



