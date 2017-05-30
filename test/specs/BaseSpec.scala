package specs

import helpers.TestData
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ Matchers, OptionValues }

import scala.concurrent.duration._

trait BaseSpec extends Matchers with ScalaFutures with OptionValues with TestData {
  implicit val defautPatience = PatienceConfig(timeout = 5 seconds, interval = 500 millis)
}
