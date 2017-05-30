package service

import models._
import scala.concurrent.Future

trait SnakesLaddersService {
  //Operations to create new player/game/move
  def createPlayer(player: Player): Future[Player]

  def createGameFor(player: Player): Future[Game]

  def newMove(newMove: NewMove): Future[MoveResult]

  //Operations to retrieve saved games (either to continue playing at a latter time or for stats)
  def getPlayerByEmail(email: String): Future[Option[Player]]

  def getGameSetupsFor(player: Player): Future[Seq[GameSetup]]

  def getLastGameInstanceFor(gameSetup: GameSetup): Future[Option[GameInstance]]

  def getGamesFor(player: Player): Future[Seq[Game]]

  def getGameFor(gameSetup: GameSetup): Future[Game]

}
