package models

import java.time.LocalDate
import play.api.libs.json._
import reactivemongo.bson.BSONObjectID

case class Board(size: Int = 100)

case class Player private (_id: BSONObjectID, name: String, email: String, dateOfBirth: LocalDate) {
  def this(name: String, email: String, dateOfBirth: LocalDate) = this(Generator.newBSONObjectID(), name, email, dateOfBirth)
}

case class Game(setup: GameSetup, instances: List[GameInstance])

case class GameSetup private (_id: BSONObjectID, board: Board, playerId: BSONObjectID) {
  def this(board: Board, playerId: BSONObjectID) = this(Generator.newBSONObjectID(), board, playerId)
}

case class GameInstance private (_id: BSONObjectID, gameId: BSONObjectID, playerId: BSONObjectID, playerPosition: Int) {
  def this(gameId: BSONObjectID, playerId: BSONObjectID, playerPosition: Int) = this(Generator.newBSONObjectID(), gameId, playerId, playerPosition)
}

case class MoveResult(nextGameInstance: GameInstance, winner: Option[Player] = None)

trait JsonCombinators {
  import reactivemongo.play.json.BSONFormats.BSONObjectIDFormat

  implicit val boardWrites: OWrites[Board] = Json.writes[Board]
  implicit val boardReads: Reads[Board] = Json.reads[Board]

  implicit val playerWrites: OWrites[Player] = Json.writes[Player]
  implicit val playerdReads: Reads[Player] = Json.reads[Player]

  implicit val gameInstanceWrites: OWrites[GameInstance] = Json.writes[GameInstance]
  implicit val gameInstanceReads: Reads[GameInstance] = Json.reads[GameInstance]

  implicit val gameSetupWrites: OWrites[GameSetup] = Json.writes[GameSetup]
  implicit val gameSetupReads: Reads[GameSetup] = Json.reads[GameSetup]

  implicit val gameWrites: Writes[Game] = Json.writes[Game]
  implicit val gameReads: Reads[Game] = Json.reads[Game]

  implicit val moveResultWrites: Writes[MoveResult] = Json.writes[MoveResult]
  implicit val moveResultReads: Reads[MoveResult] = Json.reads[MoveResult]

}

