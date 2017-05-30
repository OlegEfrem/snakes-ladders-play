package controllers

import javax.inject._
import controllers.helpers.ApiController
import play.api.libs.json.JsValue
import play.api.mvc._
import service.SnakesLaddersService

@Singleton
class Game @Inject() (service: SnakesLaddersService) extends ApiController {

  def newGameSetupForPlayer(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.Player] { player =>
      created(service.createGameSetupFor(player))
    }
  }

  def getGameSetupsByPlayer: Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.Player] { player =>
      ok(service.getGameSetupsFor(player))
    }
  }

  def getGameByGameSetup: Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.GameSetup] { gameSetup =>
      maybeItem(service.getGameFor(gameSetup))
    }
  }

  def getGamesByPlayer: Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.Player] { player =>
      ok(service.getGamesFor(player))
    }
  }

  def getLastGameInstanceForGameSetup: Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.GameSetup] { gameSetup =>
      ok(service.getLastGameInstanceFor(gameSetup))
    }
  }

  def newMove(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.NewMove] { newMove =>
      ok(service.newMove(newMove))
    }
  }

}
