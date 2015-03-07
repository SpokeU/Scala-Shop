package test

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import org.jsoup.nodes.Element
import pageobject.rozetka.RozetkaMainPage
import pageobject.TestPage
import pageobject.TestLazyPage
import pageobject.TestLazyPage

object TestCrawler {

  def writePageToFile(implicit page: Element) = {
    Files.write(Paths.get("D://result.html"), page.html.getBytes(StandardCharsets.UTF_8))
  }

  def main(args: Array[String]): Unit = {
    val test = new TestLazyPage("http://stackoverflow.com")
    val test2 = new TestLazyPage("http://stackoverflow.com")
    test.getHeader
    //test2.getHeader
  }

}