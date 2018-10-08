package services

import javax.inject.Inject
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName}

import scala.compat.java8.FutureConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BalanceService @Inject()(web3j: Web3j) {
   def getBalance(address: String, blockParameter: DefaultBlockParameter = DefaultBlockParameterName.LATEST): Future[BigInt ]= {
      web3j.ethGetBalance(address, blockParameter).sendAsync().toScala.map( _.getBalance )
   }

}