package controllers

import javax.inject._
import play.api.mvc._
import service.BalanceService

import scala.util.Try

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class BalanceController @Inject()(cc: ControllerComponents, balanceService: BalanceService) extends AbstractController(cc) {


  def getBalance() = Action { implicit request: Request[AnyContent] =>
    val publicAddress = request.body.asFormUrlEncoded.get("publicAddress").head
    Try {
      balanceService.getBalance(publicAddress)
    }.map { balance =>
      Ok(views.html.balance(Option(balance.toString())))
    }.getOrElse(Ok(views.html.balance()))
  }

  def getBalancePage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.balance(Some("Submit an address to get blance")))
  }
}
