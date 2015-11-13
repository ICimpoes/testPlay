package controllers

import models.Product
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.Messages
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller, Flash, SimpleResult}

import scala.concurrent.Future

object Products extends Controller {

  def newProduct = Action { implicit request =>
    val form = flash.get("error").fold(productForm)(
      _ => productForm.bind(flash.data))

    Ok(views.html.products.edit(form))
  }

  def list = Action.async { implicit request =>
    import scala.concurrent.ExecutionContext.Implicits.global
    val products = Future(Product.findAll.sortBy(_.name))
    products
      .map { p => Ok(views.html.products.list(p)) }
      .recover { case ex: Throwable => BadRequest(ex.getMessage) }
  }

  def show(ean: Long) = Action { implicit request =>
    Product.findByEan(ean).fold[SimpleResult](NotFound)(
      p => Ok(views.html.products.details(p)))
  }

  def save = Action { implicit request =>
    val newProductForm = productForm.bindFromRequest()

    newProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.newProduct()).
          flashing(Flash(form.data) +
            ("error" -> Messages("validation.errors")))
      },
      success = { newProduct =>
        Product.add(newProduct)
        val message = Messages("products.new.success", newProduct.name)
        Redirect(routes.Products.show(newProduct.ean)).
          flashing("success" -> message)
      }
    )
  }

  private val productForm = Form(
    mapping(
      "ean" -> longNumber.verifying(
        "validation.ean.duplicate", Product.findByEan(_).isEmpty),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  )
}
