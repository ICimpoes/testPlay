package controllers

import com.ning.http.client.Realm.AuthScheme
import play.api.libs.json._
import play.api.libs.ws.{Response, WS}
import play.api.mvc.Controller

import scala.concurrent.Future

object ExternalCall extends Controller {

  private val username = ""
  private val password = ""

  val prepareRequest: String => Future[Response] =
    WS.url(_).withAuth(username, password, AuthScheme.BASIC).get()

  private def mapResponse[R: Format](response: Response): R = {
    response.json.as[R]
  }

//  def request: Future[R] = {
//    prepareRequest("")
//      .map(mapResponse[](_)())
//  }

}

object Responses {


}
