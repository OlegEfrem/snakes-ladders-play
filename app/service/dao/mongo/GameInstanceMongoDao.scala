package service.dao.mongo

import javax.inject.{ Inject, Singleton }

import models.GameInstance
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import service.dao.GameInstanceDao

import scala.concurrent.Future

@Singleton
class GameInstanceMongoDao @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends MongoDao with GameInstanceDao {
  override val dbCollection: Future[JSONCollection] = database.map(db => db.collection[JSONCollection]("game_instances"))

  override def create(gameInstance: GameInstance): Future[GameInstance] = {
    dbCollection.flatMap(_.insert(gameInstance))
      .map(_ => gameInstance)
  }

  override def readLastByGameId(gameId: BSONObjectID): Future[Option[GameInstance]] = {
    dbCollection.flatMap(_
      .find(Json.obj("gameId" -> gameId))
      .sort(Json.obj("_id" -> -1))
      .one[GameInstance])
  }

  override def readAllByGameId(gameId: BSONObjectID): Future[Seq[GameInstance]] = {
    dbCollection.flatMap(_
      .find(Json.obj("gameId" -> gameId))
      .cursor[GameInstance]()
      .collect[List](Int.MaxValue, Cursor.FailOnError[List[GameInstance]]()))
  }

  override def deleteByPlayerId(playerId: BSONObjectID): Future[Unit] = {
    dbCollection.flatMap(_
      .remove(Json.obj("playerId" -> playerId)))
      .map(_ => ())
  }

  override def deleteByGameId(gameId: BSONObjectID): Future[Unit] = {
    dbCollection.flatMap(_
      .remove(Json.obj("gameId" -> gameId)))
      .map(_ => ())
  }

}
