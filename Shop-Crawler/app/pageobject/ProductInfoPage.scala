package pageobject

trait ProductInfoPage extends LazyPage{

  def name: String

  def details: String

  def description: String

  def characteristics: Map[String, String]

  def mainImage: String

  def mediumImages: Seq[String]

  def bigImages: Seq[String]

  def price: BigDecimal

}