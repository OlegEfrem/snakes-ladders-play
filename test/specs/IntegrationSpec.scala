package specs

import helpers.GuiceTestContext
import org.scalatest.{ BeforeAndAfterAll, WordSpec }
import service.dao.mongo.MongoDao

trait IntegrationSpec extends WordSpec with BaseSpec with GuiceTestContext with BeforeAndAfterAll {
  protected val cleanupCollections: Seq[MongoDao]

  override abstract def afterAll(): Unit = {
    cleanupCollections.foreach(_.removeAllEntries().futureValue)
  }

}
