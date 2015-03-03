package test

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

import org.jsoup.nodes.Element

import pageobject.rozetka.RozetkaMainPage

object TestCrawler{

  def writePageToFile(implicit page: Element) = {
    Files.write(Paths.get("D://result.html"), page.html.getBytes(StandardCharsets.UTF_8))
  }

  def main(args: Array[String]): Unit = {
     
  }

}