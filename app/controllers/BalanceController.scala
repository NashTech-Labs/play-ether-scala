package controllers

import javax.inject._
import play.api.mvc._
import services.BalanceService
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class BalanceController @Inject()(cc: ControllerComponents, balanceService: BalanceService) extends AbstractController(cc) {


  def getBalance() = Action.async { implicit request: Request[AnyContent] =>
      balanceService.getBalance(request.body.asFormUrlEncoded.get("publicAddress").head)
     .map { balance =>
      Ok(views.html.balance(Option(balance.toString())))
    }.recover {
        case t: Throwable => Ok(views.html.balance(Some(s"Invalid Address ${t.getMessage}")))
      }
  }

  def getBalancePage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.balance(Some("Submit an address to get blance")))
  }
}
