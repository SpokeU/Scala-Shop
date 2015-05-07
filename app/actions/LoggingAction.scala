package actions

import play.api.mvc._
import scala.concurrent.Future
import play.api.Logger

object LoggingAction extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    Logger.info("Invoked")
    block(request)
  }
}