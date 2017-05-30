package service.impl

import javax.inject.{ Inject, Singleton }

import models._
import service.SnakesLaddersService
import service.dao.{ GameInstanceDao, GameSetupDao, PlayerDao }

import scala.concurrent.Future

@Singleton
class DefaultSnakesLaddersService @Inject() (
    playerDao: PlayerDao,
    gameSetupDao: GameSetupDao,
    gameInstanceDao: GameInstanceDao
) extends SnakesLaddersService {

  def createPlayer(player: Player): Future[Player] = ???

  def getPlayerByEmail(email: String): Future[Option[Player]] = ???

  def createGameSetupFor(player: Player): Future[GameSetup] = ???

  def getGameSetupsFor(player: Player): Future[Seq[GameSetup]] = ???

  def getLastGameInstanceFor(gameSetup: GameSetup): Future[Option[GameInstance]] = ???

  def newMove(player: Player, numberOfMoves: Int, prevGameInstance: GameInstance): Future[MoveResult] = ???

  def getGamesFor(player: Player): Future[Seq[Game]] = ???

  def getGameFor(gameSetup: GameSetup): Future[Option[Game]] = ???

}
