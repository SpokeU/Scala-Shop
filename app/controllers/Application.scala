package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

import actors.ShopParserActor
import actors.StartParse
import actors.StopParse
import akka.actor.ActorSelection.toScala
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import config.DESHEVSHE
import config.Shop
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.Request
import play.libs.Akka

object Application extends Controller {

  val log = Logger("parser")
  val simpleActor = Akka.system.actorOf(Props[ShopParserActor], name = DESHEVSHE.name) /*.withRouter(RoundRobinPool(nrOfInstances = 10))*/

  def index = Action {
    println("GOT REQUEST")
    Ok("")
  }

  def startParse = Action.async(parse.json) { implicit request =>
    val shop = getShopFromRequest
    val jobId = request.body.hashCode.toString
    shop match {
      case Some(shop) => {
        implicit val timeout = Timeout(5 seconds)
        val result = simpleActor ? StartParse(jobId, DESHEVSHE)
        result.map { x => Ok(x.toString()) }
      }
      case None => Future { BadRequest("No shop found") }
    }

  }

  def stopParse = Action(parse.json) { implicit request =>
    val shop = getShopFromRequest
    val jobId = request.body.hashCode.toString
    val actorSelection = Akka.system.actorSelection(s"user/${DESHEVSHE.name}")
    shop match {
      case Some(shop) => {
        actorSelection ! StopParse(jobId, shop)
        Ok(shop.name)
      }
      case None => BadRequest("No shop found")
    }
    
  }

  def getShopFromRequest(implicit request: Request[JsValue]): Option[Shop] = {
    val shop = request.body \ "shop"
    Shop.fromString(shop.as[String])
  }
}