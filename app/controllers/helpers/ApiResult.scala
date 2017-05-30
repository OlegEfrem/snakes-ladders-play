package controllers.helpers

import play.api.i18n.Lang
import play.api.mvc.{ RequestHeader, Result }
import play.api.libs.json.Json._
import play.api.libs.json._
import play.api.mvc.Results._
import ApiError._
import ApiResponse._

/*
* The result of an ApiRequest.
*/
trait ApiResult {
  val status: Int
  val json: JsValue
  val headers: Seq[(String, String)]

  def toResult[R <: RequestHeader](implicit request: R, lang: Lang): Result = {
    val envelope = request.getQueryString("envelope").contains("true")
    toResult(envelope)
  }

  // scalastyle:off
  def toResult(envelope: Boolean = false)(implicit lang: Lang): Result = {
    val js = if (envelope) envelopedJson else json
    status match {
      case STATUS_CREATED => if (js == JsNull) Created else Created(js)
      case STATUS_ACCEPTED => if (js == JsNull) Accepted else Accepted(js)
      case STATUS_NOCONTENT => NoContent
      case s if s < 300 => if (js == JsNull) Ok else Ok(js)
      case STATUS_BADREQUEST => BadRequest(js)
      case STATUS_UNAUTHORIZED => Unauthorized(js)
      case STATUS_FORBIDDEN => Forbidden(js)
      case STATUS_NOTFOUND => NotFound(js)
      case s if s > 400 && s < 500 => BadRequest(js)
      case _ => InternalServerError(js)
    }
  }
  // scalastyle:on

  def envelopedJson(implicit lang: Lang): JsValue = Json.obj(
    "data" -> json,
    "status" -> status,
    "headers" -> JsObject((headers).map(h => h._1 -> JsString(h._2)))
  )
}
