import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import org.junit.Test
import org.junit.Assert._
import pageobject.rozetka.RozetkaProductInfoPage


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Rozetka crawler" should {
    "Parse product info page" in new WithApplication {
      val productInfoPage = new RozetkaProductInfoPage("http://rozetka.com.ua/lenovo_e10_30_59426147_black/p1192765/")
      assertEquals("Ноутбук Lenovo E10-30 (59426147) Black Суперцена!", productInfoPage.name)
      assertEquals("http://i1.rozetka.ua/goods/125842/record_125842360.jpg", productInfoPage.mainImage)
      assertNotNull(productInfoPage.price)
      assertTrue(productInfoPage.description.contains("E10 — это сверхпортативный нетбук от Lenovo, предназначенный для малого бизнеса"))
      assertTrue(productInfoPage.details.contains("""Экран 10.1" (1366x768)"""))
      assertTrue(productInfoPage.characteristics("Процессор").equals("Двухъядерный Intel Celeron N2830 (2.16 ГГц)"))
    }
  }
}
