package service

import models._
import scala.concurrent.Future

trait SnakesLaddersService {

  def createPlayer(player: Player): Future[Player]

  def getPlayerByEmail(email: String): Future[Option[Player]]

  def createGameSetupFor(player: Player): Future[GameSetup]

  def getGameSetupsFor(player: Player): Future[Seq[GameSetup]]

  def getLastGameInstanceFor(gameSetup: GameSetup): Future[Option[GameInstance]]

  def newMove(player: Player, numberOfMoves: Int, prevGameInstance: GameInstance): Future[MoveResult]

  def getGamesFor(player: Player): Future[Seq[Game]]

  def getGameFor(gameSetup: GameSetup): Future[Option[Game]]

}
