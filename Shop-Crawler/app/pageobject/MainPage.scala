package pageobject

import org.jsoup.nodes.Element

trait MainPage extends LazyPage {

  def categories: Seq[CategoryPage]

  def logo: String

}
