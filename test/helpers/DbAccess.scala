package helpers

import play.modules.reactivemongo.ReactiveMongoApi
import service.dao.mongo.{ GameInstanceMongoDao, GameSetupMongoDao, PlayerMongoDao }
import specs.BaseSpec

trait DbAccess extends BaseSpec {
  val reactiveMongoApi: ReactiveMongoApi
  val gameInstanceDao = new GameInstanceMongoDao(reactiveMongoApi)
  val gameSetupDao = new GameSetupMongoDao(reactiveMongoApi)
  val playerDao = new PlayerMongoDao(reactiveMongoApi)
}
