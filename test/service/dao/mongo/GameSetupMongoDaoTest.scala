package service.dao.mongo

import models.{ Board, GameSetup, Generator }
import service.dao.GameSetupDao
import specs.IntegrationSpec

class GameSetupMongoDaoTest extends IntegrationSpec {
  private val dao: GameSetupDao = new GameSetupMongoDao(reactiveMongoApi)

  "GameSetupMongoDao:" should {

    "save gameSetup data" in {
      val gameSetup = new GameSetup(Board(), Generator.newBSONObjectID())
      dao.readByGameId(gameSetup._id).futureValue shouldBe empty
      dao.create(gameSetup).futureValue shouldBe gameSetup
      dao.readByGameId(gameSetup._id).futureValue.value shouldBe gameSetup
    }

    "read one gameSetup entry by gameId" in {
      val gameSetup = createGameSetupInDb()
      dao.readByGameId(gameSetup._id).futureValue.value shouldBe gameSetup
    }

    "read multiple gameSetup entries by userId" in {
      val gameSetup = createGameSetupInDb()
      val gameSetup2 = createGameSetupInDb(gameSetup.copy(_id = Generator.newBSONObjectID()))
      dao.readByPlayerId(gameSetup.playerId).futureValue should contain theSameElementsAs Seq(gameSetup, gameSetup2)
    }

    "delete gameSetup data by gameId" in {
      val gameSetup = createGameSetupInDb()
      dao.readByGameId(gameSetup._id).futureValue.value shouldBe gameSetup
      dao.deleteByGameId(gameSetup._id).futureValue
      dao.readByGameId(gameSetup._id).futureValue shouldBe empty

    }

    "delete gameSetup data by userId" in {
      val gameSetup = createGameSetupInDb()
      val gameSetup2 = createGameSetupInDb(gameSetup.copy(_id = Generator.newBSONObjectID()))
      dao.readByPlayerId(gameSetup.playerId).futureValue should contain theSameElementsAs Seq(gameSetup, gameSetup2)
      dao.deleteByPlayerId(gameSetup.playerId).futureValue
      dao.readByPlayerId(gameSetup.playerId).futureValue shouldBe empty
    }

  }

  private def createGameSetupInDb(gameSetup: GameSetup = new GameSetup(Board(), Generator.newBSONObjectID())): GameSetup = {
    dao.create(gameSetup).futureValue
  }

}
