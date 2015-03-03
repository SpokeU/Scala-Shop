package pageobject

import org.jsoup.nodes.Element

trait MainPage {

  def categories: Seq[CategoryPage]
  
  def logo: String
  
}