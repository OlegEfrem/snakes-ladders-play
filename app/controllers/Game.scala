package controllers

import javax.inject._

import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Game @Inject() extends Controller {

  def newGameFor(playerName: String): Action[AnyContent] = Action { implicit request =>
    NotImplemented("Operation not implemented")
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
