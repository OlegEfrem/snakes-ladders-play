package controllers.helpers

import play.api.libs.json._
import play.api.mvc._

trait ApiRequestHeader[R <: RequestHeader] {
  val request: R

  def remoteAddress: String = request.remoteAddress

  def method: String = request.method

  def uri: String = request.uri

  def maybeBody: Option[String] = None
}

case class ApiRequestHeaderImpl(request: RequestHeader) extends ApiRequestHeader[RequestHeader]

class ApiRequest[A](val request: Request[A]) extends WrappedRequest[A](request) with ApiRequestHeader[Request[A]] {
  override def remoteAddress: String = request.remoteAddress

  override def method: String = request.method

  override def uri: String = request.uri

  override def maybeBody: Option[String] = request.body match {
    case body: JsValue => Some(Json.prettyPrint(body))
    case body: String => if (body.length > 0) Some(body) else None
    case body => Some(body.toString)
  }
}

object ApiRequest {
  def apply[A](request: Request[A]): ApiRequest[A] = new ApiRequest[A](request)
}

