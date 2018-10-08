package demo

import java.math.BigDecimal

import factory.{ElasticClientFactory, Web3ClientFactory}
import services.{BalanceService, TransferService}
import com.typesafe.config.ConfigFactory
import org.elasticsearch.index.query.QueryBuilders
import org.web3j.crypto.{Credentials, WalletUtils}
import org.web3j.protocol.core.methods.response.TransactionReceipt
import util.PimpMyTransactions.TransactionReceiptFormatter
import org.elasticsearch.common.xcontent.XContentType

object TransferDemo extends App {

  val config = ConfigFactory.load("application.conf").getConfig("transfer")
  val WALLET_PASSWORD = System.getProperty("password")
  val WALLET_SOURCE = config.getString("walletsource")
  val credentials: Credentials = WalletUtils.loadCredentials("iamnotalooser", WALLET_SOURCE)
  val INFURA_CLIENT = config.getString("infura")
  val ACCOUNT_TO = config.getString("accountto")
  val ACCOUNT_FROM = credentials.getAddress

  val web3j = Web3ClientFactory(INFURA_CLIENT).getWeb3Client()

  val balanceService = new BalanceService(web3j)

  val transferService = new TransferService(web3j)

  println("The balance of sending account :::: " + ACCOUNT_FROM + "  is:: " + balanceService.getBalance(ACCOUNT_FROM))

  println("The balance of receiving account :::: " + ACCOUNT_TO + "  is:: " + balanceService.getBalance(ACCOUNT_TO))

  val transactionReceipt: TransactionReceipt = transferService.transferEther(credentials, ACCOUNT_TO, BigDecimal.valueOf(0.000001))

  println(s"The transaction receipt is:::::::::::${transactionReceipt.toJSON}")

  val client = ElasticClientFactory.getESClient

  client.prepareIndex("receipts", "transfers", "blocknumber")
    .setSource(transactionReceipt.toJSON, XContentType.JSON).get()
  println("Added Index.....")

  val searchResult = client.prepareSearch("receipts").setTypes("transfers").setQuery(QueryBuilders.termQuery("_id", "1")).get()

  println(s"The search response is::::::${searchResult.toString}")

  transactionReceipt.getContractAddress

  println("The balance of sending account " + ACCOUNT_FROM + "  after sending " + BigDecimal.valueOf(0.000001) + " eth is:: " + balanceService.getBalance(ACCOUNT_FROM))

  println("The balance of receiving account :::: " + ACCOUNT_TO + "  after receiving " + BigDecimal.valueOf(0.000001) + " eth is:: " + balanceService.getBalance(ACCOUNT_TO))

  println("Completed Transfer.... \nstatus =  " + transactionReceipt.getStatus + "\nThe transaction hash " + transactionReceipt.getTransactionHash)

}