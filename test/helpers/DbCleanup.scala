package helpers

import org.scalatest.{ BeforeAndAfterAll, TestSuite }
import service.dao.mongo.MongoDao
import specs.BaseSpec

trait DbCleanup extends BaseSpec with GuiceTestContext with BeforeAndAfterAll {
  this: TestSuite =>
  protected val cleanupCollections: Seq[MongoDao]

  override abstract def beforeAll(): Unit = {
    cleanupCollections.foreach(_.removeAllEntries().futureValue)
  }

}
