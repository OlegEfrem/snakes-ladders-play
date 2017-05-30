package controllers

import javax.inject._

import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Player @Inject() extends Controller {

  def newPlayer(playerName: String): Action[AnyContent] = Action { implicit request =>
    NotImplemented("Operation not implemented")
  }

  def getPlayerBy(playerName: String): Action[AnyContent] = Action { implicit request =>
    NotImplemented("Operation not implemented")
  }

}
