package helpers

import play.api.inject.guice.GuiceApplicationBuilder
import play.modules.reactivemongo.ReactiveMongoApi

trait GuiceTestContext {
  lazy val app = new GuiceApplicationBuilder()
    .overrides()
    .build()
  val reactiveMongoApi = app.injector.instanceOf[ReactiveMongoApi]
}
