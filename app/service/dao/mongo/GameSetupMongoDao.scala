package service.dao.mongo

import javax.inject.{ Inject, Singleton }
import models.GameSetup
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import service.dao.GameSetupDao
import scala.concurrent.Future

@Singleton
class GameSetupMongoDao @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends MongoDao with GameSetupDao {
  override val dbCollection: Future[JSONCollection] = database.map(db => db.collection[JSONCollection]("game_setups"))

  override def create(gameSetup: GameSetup): Future[GameSetup] = {
    dbCollection.flatMap(_.insert(gameSetup))
      .map(_ => gameSetup)
  }

  override def readByGameId(gameId: BSONObjectID): Future[Option[GameSetup]] = {
    dbCollection.flatMap(_
      .find(Json.obj("_id" -> gameId))
      .one[GameSetup])
  }

  override def readByPlayerId(playerId: BSONObjectID): Future[Seq[GameSetup]] = {
    dbCollection.flatMap(_
      .find(Json.obj("playerId" -> playerId))
      .cursor[GameSetup]()
      .collect[List](Int.MaxValue, Cursor.FailOnError[List[GameSetup]]()))
  }

  override def deleteByPlayerId(playerId: BSONObjectID): Future[Unit] = {
    dbCollection.flatMap(_
      .remove(Json.obj("playerId" -> playerId)))
      .map(_ => ())
  }

  override def deleteByGameId(gameId: BSONObjectID): Future[Unit] = {
    dbCollection.flatMap(_
      .remove(Json.obj("_id" -> gameId)))
      .map(_ => ())
  }
}
