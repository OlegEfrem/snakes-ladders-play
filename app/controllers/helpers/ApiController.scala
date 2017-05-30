package controllers.helpers

import controllers.helpers.ApiError._
import models.JsonCombinators
import util.ActorContext
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.Future

trait ApiController extends Controller with JsonCombinators with ActorContext {

  implicit def objectToJson[T](o: T)(implicit tjs: Writes[T]): JsValue = Json.toJson(o)

  implicit def result2FutureResult(r: ApiResult): Future[ApiResult] = Future.successful(r)

  implicit def apiResultToResult(r: ApiResult): Result = r.toResult()

  implicit def futApiResultToFutResult(r: Future[ApiResult]): Future[Result] = r.map(_.toResult())

  def ok[A](futObj: Future[A], headers: (String, String)*)(implicit w: Writes[A]): Future[ApiResult] = futObj.map(obj => ApiResponse.ok(obj, headers: _*))

  def maybeItem[A](futOpt: Future[Option[A]], headers: (String, String)*)(implicit w: Writes[A], req: RequestHeader): Future[ApiResult] =
    futOpt.map(opt => itemOrError(opt, headers: _*))

  private def itemOrError[A](opt: Option[A], headers: (String, String)*)(implicit w: Writes[A], req: RequestHeader): ApiResult = opt match {
    case Some(i) => ApiResponse.ok(i, headers: _*)
    case None => ApiError.errorItemNotFound
  }

  def created[A](futObj: Future[A], headers: (String, String)*)(implicit w: Writes[A]): Future[ApiResult] =
    futObj.map(obj => ApiResponse.created(obj, headers: _*))

  def noContent(headers: (String, String)*): Future[ApiResult] = Future.successful(ApiResponse.noContent(headers: _*))

  def readFromRequest[T](f: T => Future[ApiResult])(implicit request: Request[JsValue], rds: Reads[T], req: RequestHeader): Future[ApiResult] = {
    request.body.validate[T].fold(
      errors => errorBodyMalformed(errors),
      readValue => f(readValue)
    )
  }

}
