package service.dao.mongo

import models.{Generator, Player}
import service.dao.PlayerDao
import specs.IntegrationSpec

class PlayerMongoDaoTest extends IntegrationSpec {
  private val dao: PlayerDao = new PlayerMongoDao(reactiveMongoApi)

  "PlayerMongoDao" should {

    "save a player" in {
      val player = new Player(randomString, randomEmail, randomDateOfBirth)
      dao.readBy(player.email).futureValue shouldBe empty
      dao.create(player).futureValue
      dao.readBy(player._id).futureValue.value shouldBe player
    }

    "throw an error when saving user with duplicate email" in {
      val player = createPlayerInDb()
      a[Exception] shouldBe thrownBy {
        dao.create(player.copy(_id = Generator.newBSONObjectID())).futureValue
      }
    }

    "retrieve a player by email" in {
      val player = createPlayerInDb()
      dao.readBy(player.email).futureValue.value shouldBe player
    }

    "retrieve a player by id" in {
      val player = createPlayerInDb()
      dao.readBy(player._id).futureValue.value shouldBe player
    }

    "update a player" in {
      val player = createPlayerInDb()
      dao.readBy(player._id).futureValue.value shouldBe player
      val updatedPlayer = player.copy(email = "new@email.com")
      dao.update(updatedPlayer).futureValue
      dao.readBy(player._id).futureValue.value shouldBe updatedPlayer
    }

    "delete a player" in {
      val player = createPlayerInDb()
      dao.readBy(player._id).futureValue.value shouldBe player
      dao.delete(player).futureValue
      dao.readBy(player._id).futureValue shouldBe empty

    }

  }

  private def createPlayerInDb(player: Player = new Player(randomString, randomEmail, randomDateOfBirth)): Player = {
    dao.create(player).futureValue
  }

}
