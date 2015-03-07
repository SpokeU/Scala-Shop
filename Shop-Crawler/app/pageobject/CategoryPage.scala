package pageobject

trait CategoryPage {

  def name: String

  def products: Seq[ProductInfoPage]

  def subCategories: Seq[CategoryPage]

}