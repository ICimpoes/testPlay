package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Redirect(routes.Products.list())
  }

  def hello(n: String) = Action {
    Ok(views.html.hello(n))
  }
}