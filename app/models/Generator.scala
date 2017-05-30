package models

import reactivemongo.bson.BSONObjectID

object Generator {

  def newBSONObjectID(): BSONObjectID = BSONObjectID.generate()

}
