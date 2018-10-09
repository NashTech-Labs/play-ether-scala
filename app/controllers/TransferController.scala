package controllers

import javax.inject._
import play.api.mvc._
import services.{IndexingService, TransferService}
import util.ConfigProvider
import util.PimpMyTransactions.TransactionReceiptFormatter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TransferController @Inject()(cc: ControllerComponents, transferService: TransferService) extends AbstractController(cc) {

  def transferFunds() = Action.async { implicit request: Request[AnyContent] =>
    val toAccount = request.body.asFormUrlEncoded.get("accountTo").head
    val amount = request.body.asFormUrlEncoded.get("amount").head
    transferService.transferEther(ConfigProvider.credentials, toAccount, java.math.BigDecimal.valueOf(amount.toDouble))
    .map { reciept =>
        Ok(views.html.transfer(Option(reciept.toJSON)))
      }.recover { case ex: Throwable => Ok(views.html.transfer(Some(ex.getMessage)))
    }
  }

  def getTransferPage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.transfer(Some("Submit details to Transfer funds!")))
  }

}
