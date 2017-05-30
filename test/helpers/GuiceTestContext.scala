package helpers

import org.scalatest.TestSuite
import org.scalatestplus.play.guice.{ GuiceOneAppPerSuite, GuiceOneAppPerTest }
import play.api.inject.guice.GuiceApplicationBuilder
import play.modules.reactivemongo.ReactiveMongoApi

trait GuiceTestContext extends GuiceOneAppPerSuite { this: TestSuite =>
  val reactiveMongoApi = app.injector.instanceOf[ReactiveMongoApi]
}
