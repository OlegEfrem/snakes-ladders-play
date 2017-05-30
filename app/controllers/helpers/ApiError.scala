package controllers.helpers

import play.api.data.validation.ValidationError
import play.api.libs.json._

case class ApiError(code: Int, msg: String, info: Option[JsValue]) extends ApiResult {
  val status: Int = ApiError.statusFromCode(code)
  val json: JsValue = ApiError.json(code, msg, info)
  val headers: Seq[(String, String)] = Seq()
}

object ApiError {

  final val STATUS_BADREQUEST = 400
  final val STATUS_UNAUTHORIZED = 401

  //////////////////////////////////////////////////////////////////////
  // Status Codes
  final val STATUS_FORBIDDEN = 403
  final val STATUS_NOTFOUND = 404
  final val STATUS_INTERNAL_SERVER = 500
  final val ERROR_CUSTOM = 0
  final val ERROR_PARAM_MALFORMED = 121

  //////////////////////////////////////////////////////////////////////
  // Error Codes
  final val ERROR_HEADER_MISSED = 122
  final val ERROR_HEADER_MALFORMED = 123
  final val ERROR_BODY_MISSED = 124
  final val ERROR_BODY_MALFORMED = 125
  final val ERROR_ITEM_NOTFOUND = 130
  final val ERROR_BADREQUEST = 400
  final val ERROR_UNAUTHORIZED = 401
  final val ERROR_FORBIDDEN = 403
  final val ERROR_NOTFOUND = 404
  final val ERROR_INTERNAL_SERVER = 500

  def errorBodyMalformed(errors: Seq[(JsPath, Seq[ValidationError])]): ApiError =
    apply(ERROR_BODY_MALFORMED, "Body Malformed", JsObject(errors.map { e =>
      e._1.toJsonString -> JsArray(e._2.map { ve =>
        JsString(s"${ve.message} => ${ve.args.mkString(";")}")
      })
    }))

  def apply(code: Int, msg: String, info: JsValue): ApiError = ApiError(code, msg, Some(info))

  def errorItemNotFound: ApiError = apply(ERROR_ITEM_NOTFOUND, "Item Not Found")

  def apply(code: Int, msg: String): ApiError = ApiError(code, msg, None)

  def json(code: Int, msg: String, info: Option[JsValue]): JsValue = {
    val o = Json.obj(
      "code" -> code,
      "message" -> msg
    )
    info.map(i => o + ("info" -> i)).getOrElse(o)
  }

  def statusFromCode(code: Int): Int = code match {
    case ERROR_BADREQUEST => STATUS_BADREQUEST
    case ERROR_UNAUTHORIZED => STATUS_UNAUTHORIZED
    case ERROR_FORBIDDEN => STATUS_FORBIDDEN
    case ERROR_NOTFOUND => STATUS_NOTFOUND
    case ERROR_INTERNAL_SERVER => STATUS_INTERNAL_SERVER
    case _ => STATUS_BADREQUEST
  }

}
