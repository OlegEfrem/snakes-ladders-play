package service.impl

import javax.inject.{ Inject, Singleton }

import models._
import service.SnakesLaddersService
import service.dao.{ GameInstanceDao, GameSetupDao, PlayerDao }
import util.ActorContext

import scala.concurrent.Future

@Singleton
class DefaultSnakesLaddersService @Inject() (
    playerDao: PlayerDao,
    gameSetupDao: GameSetupDao,
    gameInstanceDao: GameInstanceDao
) extends SnakesLaddersService with ActorContext {

  def createPlayer(player: Player): Future[Player] = playerDao.create(player)

  def getPlayerByEmail(email: String): Future[Option[Player]] = playerDao.readBy(email)

  def createGameFor(player: Player): Future[Game] = {
    for {
      gameSetup <- gameSetupDao.create(new GameSetup(Board(), player._id))
      gameInstance <- gameInstanceDao.create(new GameInstance(gameSetup._id, player._id, 1))
    } yield Game(gameSetup, Seq(gameInstance))
  }

  def getGameSetupsFor(player: Player): Future[Seq[GameSetup]] = gameSetupDao.readByPlayerId(player._id)

  def getLastGameInstanceFor(gameSetup: GameSetup): Future[Option[GameInstance]] = gameInstanceDao.readLastByGameId(gameSetup._id)

  def newMove(newMove: NewMove): Future[MoveResult] = {
    val newPosition = newMove.prevGameInstance.playerPosition + newMove.numberOfMoves
    val nextGameIntance = new GameInstance(newMove.gameSetup._id, newMove.player._id, newPosition)
    gameInstanceDao.create(nextGameIntance).map(gi => MoveResult(gi, None))
  }

  def getGamesFor(player: Player): Future[Seq[Game]] = ???

  def getGameFor(gameSetup: GameSetup): Future[Game] = {
    gameInstanceDao.readAllByGameId(gameSetup._id)
      .map(gs => Game(gameSetup, gs))
  }

}
