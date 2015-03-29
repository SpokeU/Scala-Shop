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
    val list = List(1, 2, 3, 4)
    println(List(1, 2, 3, 4).collect { case x: Int if x == 1 => x })
    list match {
      case Nil => println("Jopa")
      case x: List[Int] => println("Gabamola")
    }
  }

}
