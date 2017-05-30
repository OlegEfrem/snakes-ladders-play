package util

import scala.concurrent.ExecutionContext

trait ActorContext {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
}
