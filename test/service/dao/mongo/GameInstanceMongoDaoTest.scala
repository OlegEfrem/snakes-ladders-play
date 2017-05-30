package service.dao.mongo

import models.{ Board, GameInstance, Generator }
import org.joda.time.DateTime
import reactivemongo.bson.BSONObjectID
import service.dao.GameInstanceDao
import specs.IntegrationSpec

class GameInstanceMongoDaoTest extends IntegrationSpec {
  private val dao: GameInstanceDao = new GameInstanceMongoDao(reactiveMongoApi)

  "gameInstanceMongoDao:" should {

    "save gameInstance data" in {
      val gameInstance = newGameInstance()
      dao.readLastByGameId(gameInstance.gameId).futureValue shouldBe empty
      dao.create(gameInstance).futureValue shouldBe gameInstance
      dao.readLastByGameId(gameInstance.gameId).futureValue.value shouldBe gameInstance
    }

    "read last entered gameInstance entry by gameId" in {
      val gameInstance = creategameInstanceInDb()
      val gameInstance2 = gameInstance.copy(_id = BSONObjectID.fromTime(DateTime.now().plusHours(1).getMillis))
      creategameInstanceInDb(gameInstance2)
      dao.readLastByGameId(gameInstance.gameId).futureValue.value shouldBe gameInstance2
    }

    "read multiple gameInstances entries by gameId" in {
      val gameInstance = creategameInstanceInDb()
      val gameInstance2 = creategameInstanceInDb(gameInstance.copy(_id = Generator.newBSONObjectID(), playerPosition = 10))
      dao.readAllByGameId(gameInstance.gameId).futureValue should contain theSameElementsAs Seq(gameInstance, gameInstance2)
    }

    "delete gameInstance data by gameId" in {
      val gameInstance = creategameInstanceInDb()
      dao.readLastByGameId(gameInstance.gameId).futureValue.value shouldBe gameInstance
      dao.deleteByGameId(gameInstance.gameId).futureValue
      dao.readLastByGameId(gameInstance.gameId).futureValue shouldBe empty

    }

    "delete gameInstance data by playerId" in {
      val gameInstance = creategameInstanceInDb()
      val gameInstance2 = creategameInstanceInDb(gameInstance.copy(_id = Generator.newBSONObjectID()))
      dao.readAllByGameId(gameInstance.gameId).futureValue should contain theSameElementsAs Seq(gameInstance, gameInstance2)
      dao.deleteByPlayerId(gameInstance.playerId).futureValue
      dao.readAllByGameId(gameInstance.gameId).futureValue shouldBe empty
    }

  }

  private def newGameInstance() = new GameInstance(Generator.newBSONObjectID(), Generator.newBSONObjectID(), 1)

  private def creategameInstanceInDb(gameInstance: GameInstance = newGameInstance()): GameInstance = {
    dao.create(gameInstance).futureValue
  }
}
