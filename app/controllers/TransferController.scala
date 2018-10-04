package controllers

import javax.inject._
import play.api.mvc._
import service.TransferService
import util.ConfigProvider
import util.PimpMyTransactions.TransactionReceiptFormatter

import scala.util.{Failure, Success, Try}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TransferController @Inject()(cc: ControllerComponents, transferService: TransferService) extends AbstractController(cc) {

  def transferFunds() = Action { implicit request: Request[AnyContent] =>
    val toAccount = request.body.asFormUrlEncoded.get("accountTo").head
    val amount = request.body.asFormUrlEncoded.get("amount").head
    val possibleReceipt = Try {transferService.transferEther(ConfigProvider.credentials, toAccount, java.math.BigDecimal.valueOf(amount.toDouble)) }
    possibleReceipt match {
      case Success(reciept) => Ok(views.html.transfer(Option(reciept.toJSON)))
      case Failure(ex) => ex.printStackTrace()
        Ok(views.html.transfer(Some(ex.getMessage)))
    }
  }

  def getTransferPage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.transfer())
  }


}
