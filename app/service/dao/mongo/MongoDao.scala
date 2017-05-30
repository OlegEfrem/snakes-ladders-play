package service.dao.mongo

import models.JsonCombinators
import play.api.libs.json.Json
import play.modules.reactivemongo.{ MongoController, ReactiveMongoComponents }
import reactivemongo.play.json.collection.JSONCollection
import util.ActorContext
import scala.concurrent.Future
import reactivemongo.play.json._

trait MongoDao extends MongoController with ReactiveMongoComponents with JsonCombinators with ActorContext {
  val dbCollection: Future[JSONCollection]

  def removeAllEntries(): Future[Unit] = dbCollection.flatMap(_.remove(Json.obj())).map(_ => ())
}
