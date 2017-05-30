package helpers

import play.api.inject.guice.GuiceApplicationBuilder
import play.modules.reactivemongo.ReactiveMongoApi

trait GuiceTestContext {
  val app = new GuiceApplicationBuilder().build()
  val reactiveMongoApi = app.injector.instanceOf[ReactiveMongoApi]
}