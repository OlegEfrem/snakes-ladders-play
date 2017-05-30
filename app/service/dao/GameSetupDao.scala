package service.dao

import models.GameSetup
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

trait GameSetupDao {

  def create(gameSetup: GameSetup): Future[GameSetup]

  def readByGameId(gameId: BSONObjectID): Future[Option[GameSetup]]

  def readByPlayerId(playeId: BSONObjectID): Future[Seq[GameSetup]]

  def deleteByPlayerId(playerId: BSONObjectID): Future[Unit]

  def deleteByGameId(gameId: BSONObjectID): Future[Unit]

}
