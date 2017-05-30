package service.dao.mongo

import models.JsonCombinators
import play.modules.reactivemongo.{ MongoController, ReactiveMongoComponents }
import util.ActorContext

trait MongoDao extends MongoController with ReactiveMongoComponents with JsonCombinators with ActorContext
