package service.dao

import models.Player
import reactivemongo.bson.BSONObjectID
import util.ActorContext

import scala.concurrent.Future

trait PlayerDao extends ActorContext {

  def create(player: Player): Future[Player]

  def readBy(email: String): Future[Option[Player]]

  def readBy(id: BSONObjectID): Future[Option[Player]]

  def update(player: Player): Future[Unit]

  def delete(player: Player): Future[Unit]

}
