package bdd

import helpers.DbAccess
import models._
import org.scalatest.{ FeatureSpec, GivenWhenThen }
import play.api.libs.json.Json
import play.api.test.Helpers._
import specs.RestApiSpec

class SnakesLaddersTest extends FeatureSpec with RestApiSpec with GivenWhenThen with DbAccess {
  override protected val cleanupCollections = Seq(playerDao, gameSetupDao, gameInstanceDao)

  feature("Feature 1 - Moving Your Token") {

    scenario("Token Can Move Across the Board") {
      info("As a player")
      val player: Player = createPlayer
      info("I want to be able to move my token")
      info("So that I can get closer to the goal")

      Given("the game is started")
      val game: Game = createGame(player)
      When("the token is placed on the board")
      val gameInstance = game.instances.head
      Then("the token is on square 1")
      gameInstance.playerPosition shouldBe 1

      Given("the token is on square 1")
      gameInstance.playerPosition shouldBe 1
      When("the token is moved 3 spaces")
      val moveResult = makeMove(player, game, gameInstance, 3)
      Then("the token is on square 4")
      moveResult.nextGameInstance.playerPosition shouldBe 4

      Given("the token is on square 1")
      gameInstance.playerPosition shouldBe 1
      When("the token is moved 3 spaces")
      val moveResult2 = makeMove(player, game, gameInstance, 3)
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
      val player = createPlayer
      val game = createGame(player)
      When("the player rolls a die")
      val diceRollResponse = routeGET("/dice/roll")
      status(diceRollResponse) shouldBe OK
      Then("the result should be between 1-6 inclusive")
      val diceResult = (contentAsJson(diceRollResponse) \ "diceResult").as[Int]
      diceResult should be >= 1
      diceResult should be <= 6

      Given("the player rolls a 4")
      val gameInstance = game.instances.head
      val diceResult1 = 4
      When("they move their token")
      val moveResult = makeMove(player, game, gameInstance, diceResult1)
      Then("the token should move 4 spaces")
      val expectedPosition = gameInstance.playerPosition + diceResult1
      moveResult.nextGameInstance.playerPosition shouldBe expectedPosition
    }

    scenario("Player Can Win the Game") {
      info("As a player")
      val player = createPlayer
      info("I want to be able to win the game")
      val game = createGame(player)
      info("So that I can gloat to everyone around")

      Given("the token is on square 97")
      val gameInstance = game.instances.head.copy(playerPosition = 97)
      When("the token is moved 3 spaces")
      val moveResult = makeMove(player, game, gameInstance, 3)
      Then("the token is on square 100")
      moveResult.nextGameInstance.playerPosition == 100
      And("the player has won the game")
      moveResult.winner.value shouldBe player

      Given("the token is on square 97")
      gameInstance.playerPosition shouldBe 97
      When("the token is moved 4 spaces")
      val moveResult2 = makeMove(player, game, gameInstance, 4)
      Then("Then the token is on square 97")
      moveResult2.nextGameInstance.playerPosition shouldBe 97
      And("the player has not won the game")
      moveResult2.winner shouldBe empty
    }

  }
  val playerName = "SomePlayerName"

  private def createGame(player: Player): Game = {
    val createGameResponse = routePUT("/game", Json.toJson(player))
    status(createGameResponse) shouldBe CREATED
    contentAsJson(createGameResponse).validate[Game].asOpt.value
  }

  private def createPlayer: Player = {
    val player = new Player(randomString, randomEmail, randomDateOfBirth)
    val createPlayerResponse = routePUT("/player", Json.toJson(player))
    status(createPlayerResponse) shouldBe CREATED
    contentAsJson(createPlayerResponse).validate[Player].asOpt.value
  }

  private def makeMove(player: Player, game: Game, prevGameInstance: GameInstance, noOfMoves: Int): MoveResult = {
    val newMove = NewMove(player, noOfMoves, prevGameInstance, game.setup)
    val moveResponse = routePOST("/game/move", Json.toJson(newMove))
    status(moveResponse) shouldBe OK
    contentAsJson(moveResponse).validate[MoveResult].asOpt.value
  }
}
