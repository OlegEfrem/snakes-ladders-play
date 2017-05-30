package service.dao

import models.GameInstance
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

trait GameInstanceDao {

  def create(gameInstance: GameInstance): Future[GameInstance]

  def readLastByGameId(gameId: BSONObjectID): Future[Option[GameInstance]]

  def readAllByGameId(gameId: BSONObjectID): Future[Seq[GameInstance]]

  def deleteByPlayerId(playerId: BSONObjectID): Future[Unit]

  def deleteByGameId(gameId: BSONObjectID): Future[Unit]

}
