package controllers

import javax.inject._
import controllers.helpers.ApiController
import play.api.mvc._
import service.SnakesLaddersService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Game @Inject() (service: SnakesLaddersService) extends ApiController {

  def newGameFor(playerName: String) = Action.async(parse.json) { implicit request =>
    readFromRequest[models.Player] { player =>
      Created(service.createPlayer(player))
    }
  }

  def getGamesFor(playerName: String): Action[AnyContent] = Action { implicit request =>
    NotImplemented("Operation not implemented")
  }

  def getGameBy(gameId: String): Action[AnyContent] = Action { implicit request =>
    NotImplemented("Operation not implemented")
  }

  def newMoveFor(gameId: String, playerId: String): Action[AnyContent] = Action { implicit request =>
    NotImplemented("Operation not implemented")
  }

}
