package controllers

import javax.inject._

import controllers.helpers.ApiController
import models.Dice
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import service.SnakesLaddersService

@Singleton
class Game @Inject() (service: SnakesLaddersService) extends ApiController {
  //Start of operations to create and play a new game
  def newGameForPlayer(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.Player] { player =>
      created(service.createGameFor(player))
    }
  }

  def newMove(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.NewMove] { newMove =>
      ok(service.newMove(newMove))
    }
  }

  def rollDice(): Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj("diceResult" -> Dice.roll))
  }

  //End of operations to create and play a new game

  //Start of operations to retrieve saved games
  def getGameSetupsByPlayer: Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.Player] { player =>
      ok(service.getGameSetupsFor(player))
    }
  }

  def getGameByGameSetup: Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.GameSetup] { gameSetup =>
      ok(service.getGameFor(gameSetup))
    }
  }

  def getLastGameInstanceForGameSetup: Action[JsValue] = Action.async(parse.json) { implicit request =>
    readFromRequest[models.GameSetup] { gameSetup =>
      ok(service.getLastGameInstanceFor(gameSetup))
    }
  }

  //End of operations to retrieve saved games

}
