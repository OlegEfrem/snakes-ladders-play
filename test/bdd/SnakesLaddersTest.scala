package bdd

import helpers.DbAccess
import models._
import org.scalatest.{ FeatureSpec, GivenWhenThen }
import play.api.libs.json.Json
import play.api.test.Helpers._
import specs.RestApiSpec

class SnakesLaddersTest extends FeatureSpec with RestApiSpec with GivenWhenThen with DbAccess {
  val playerName = "SomePlayerName"

  feature("Feature 1 - Moving Your Token") {

    scenario("Token Can Move Across the Board") {
      info("As a player")
      val player = new Player(randomString, randomEmail, randomDateOfBirth)
      val craetePlayerResponse = routePUT("/player", Json.toJson(player))
      status(craetePlayerResponse) shouldBe CREATED
      info("I want to be able to move my token")
      info("So that I can get closer to the goal")

      Given("the game is started")
      val createGameResponse = routePUT("/game", Json.toJson(player))
      status(createGameResponse) shouldBe CREATED
      When("the token is placed on the board")
      val game = contentAsJson(createGameResponse).validate[Game].asOpt.value
      Then("the token is on square 1")
      game.instances.head.playerPosition shouldBe 1

      Given("the token is on square 1")
      game.instances.head.playerPosition shouldBe 1
      When("the token is moved 3 spaces")
      val moveResult = makeMove(player, game, game.instances.head, 3)
      Then("the token is on square 4")
      moveResult.nextGameInstance.playerPosition shouldBe 4

      Given("the token is on square 1")
      game.instances.head.playerPosition shouldBe 1
      When("the token is moved 3 spaces")
      val moveResult2 = makeMove(player, game, game.instances.head, 3)
      And("then it is moved 4 spaces")
      val moveResult3 = makeMove(player, game, moveResult2.nextGameInstance, 4)
      Then("the token is on square 8")
      moveResult3.nextGameInstance.playerPosition shouldBe 8

    }

    scenario("Moves Are Determined By Dice Rolls") {
      info("As a player")
      info("I want to move my token based on the roll of a die")
      info("So that there is an element of chance in the game")

      Given("the game is started")
      When("the player rolls a die")
      Then("the result should be between 1-6 inclusive")

      Given("the player rolls a 4")
      When("they move their token")
      Then("the token should move 4 spaces")

      pending
    }

    scenario("Player Can Win the Game") {
      info("As a player")
      info("I want to be able to win the game")
      info("So that I can gloat to everyone around")

      Given("the token is on square 97")
      When("the token is moved 3 spaces")
      Then("the token is on square 100")
      And("the player has won the game")

      Given("the token is on square 97")
      When("the token is moved 4 spaces")
      Then("the token should move 4 spaces")
      And("the player has not won the game")

      pending
    }

  }

  private def makeMove(player: Player, game: Game, prevGameInstance: GameInstance, noOfMoves: Int): MoveResult = {
    val newMove = NewMove(player, noOfMoves, prevGameInstance, game.setup)
    val moveResponse = routePOST("/game/move", Json.toJson(newMove))
    status(moveResponse) shouldBe OK
    contentAsJson(moveResponse).validate[MoveResult].asOpt.value
  }
}
