package specs

import helpers.DbCleanup
import models.JsonCombinators
import org.scalatest.TestSuite
import play.api.http.Writeable
import play.api.mvc.{ AnyContentAsEmpty, Headers, Result }
import play.api.test.FakeRequest
import play.api.test.Helpers.{ route, _ }
import scala.concurrent.Future

trait RestApiSpec extends DbCleanup with JsonCombinators {
  this: TestSuite =>
  private val printInputs = false

  def routeGET(uri: String): Future[Result] =
    getRoute(GET, uri, AnyContentAsEmpty)

  def routePOST[A](uri: String, body: A)(implicit w: Writeable[A]): Future[Result] =
    getRoute(POST, uri, body)

  def routePUT[A](uri: String, body: A)(implicit w: Writeable[A]): Future[Result] =
    getRoute(PUT, uri, body)

  def routeDELETE(uri: String): Future[Result] =
    getRoute(DELETE, uri, AnyContentAsEmpty)

  private def getRoute[A](method: String, uri: String, body: A, headers: Headers = Headers())(implicit w: Writeable[A]): Future[Result] = {
    if (printInputs) print(method, uri, body, headers)
    route(app, FakeRequest(method, uri, headers, body)).get
  }

  private def print[A](method: String, uri: String, body: A, headers: Headers): Unit = {
    println(s"method: $method, uri: $uri")
    println("headers:" + headers.toSimpleMap.mkString(""))
    println("body:" + body)
  }

}