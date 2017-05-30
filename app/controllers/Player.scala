package controllers

import javax.inject._

import controllers.helpers.ApiController
import play.api.libs.json.JsValue
import play.api.mvc._
import service.SnakesLaddersService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Player @Inject() (service: SnakesLaddersService) extends ApiController {

  def newPlayer(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.Player] { player =>
      created(service.createPlayer(player))
    }
  }

  def getPlayerBy(email: String): Action[AnyContent] = Action.async { implicit request =>
    maybeItem(service.getPlayerByEmail(email))
  }

}
