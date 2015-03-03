package pageobject

trait ProductInfoPage {

  def name: String

  def details: String

  def description: String

  def characteristics: Map[String, String]

  def mainImage: String

  def mediumImages: Seq[String]

  def bigImages: Seq[String]

  def price: BigDecimal

}