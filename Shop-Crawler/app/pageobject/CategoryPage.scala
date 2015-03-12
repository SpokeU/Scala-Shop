package pageobject

trait CategoryPage {

  /**
   * Returns name of category
   */
  def name: String

  /**
   * Products for this category. If this category has sub categories then it will probably be Nil
   */
  def products: Seq[ProductInfoPage]

  /**
   * List of SubCategories for this category.
   * For example:
   * Electronics
   *  -NOTEBOOKS & ACCESORRIES
   *    -NETBOOKS
   *
   * Sub categories for Electronics will be "NOTEBOOKS & ACCESORRIES"
   * and for "NOTEBOOKS & ACCESORRIES" it is "NETBOOKS" etc.
   */
  def subCategories: Seq[CategoryPage]

}