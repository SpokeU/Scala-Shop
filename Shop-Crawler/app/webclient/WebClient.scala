package webclient

import org.jsoup.nodes.Element
import org.jsoup.Jsoup
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import net.anthavio.phanbedder.Phanbedder
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.interactions.Actions
import java.util.concurrent.TimeUnit

object WebClient {

  val phantomJS = {
    val phantomjs = Phanbedder.unpack();
    val dcaps = new DesiredCapabilities();
    dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
    val driver = new PhantomJSDriver(dcaps);
    driver.manage.timeouts.implicitlyWait(15, TimeUnit.SECONDS)
    driver
  }

  def getPage(url: String): Element = {
    Jsoup.connect(url).timeout(30 * 1000).get
  }

}

sealed trait WebClientType
object WebClientType {
  case object JSOUP extends WebClientType
  case object PHANTOM_JS extends WebClientType
}

