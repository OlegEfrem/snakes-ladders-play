package service.dao.mongo

import javax.inject.{ Inject, Singleton }
import models.Player
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.indexes.{ Index, IndexType }
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import service.dao.PlayerDao
import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }

@Singleton
class PlayerMongoDao @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends MongoDao with PlayerDao {
  private val players: Future[JSONCollection] = database.map(db => db.collection[JSONCollection]("players"))
  Await.result(players.map(_.indexesManager.ensure(Index(Seq("email" -> IndexType.Text), unique = true))), 10 seconds)

  override def create(player: Player): Future[Player] = {
    players.flatMap(_
      .insert(player))
      .map(_ => player)
  }

  override def readBy(email: String): Future[Option[Player]] = {
    players.flatMap(_
      .find(Json.obj("email" -> email))
      .one[Player])
  }

  override def readBy(id: BSONObjectID): Future[Option[Player]] = {
    players.flatMap(_
      .find(Json.obj("_id" -> id))
      .one[Player])
  }

  override def update(player: Player): Future[Unit] = {
    players.flatMap(_
      .update(Json.obj("_id" -> player._id), player))
      .map(_ => ())
  }

  override def delete(player: Player): Future[Unit] = {
    players.flatMap(_
      .remove(Json.obj("_id" -> player._id)))
      .map(_ => ())
  }

}
