package services

import org.scalatest.AsyncFlatSpec
import util.ConfigProvider

class BalanceServiceTest extends AsyncFlatSpec {

  val validAddress = "0xD286C7cB356c26Ff5D31097Fc17aca3DD29F0a51"

  val balanceService = new BalanceService(ConfigProvider.web3j)

  behavior of "getBalance"

   it should  "eventually return a BigInt for a valid address" in {
     val possibleBalance  = balanceService.getBalance(validAddress)
     possibleBalance map { balance => assert(balance >= 0) }
  }

  it should " eventually return an Exception for an invalid address" in {

    balanceService.getBalance("invalidAddress").failed.map { t: Throwable =>
       assert(t.getMessage.contains("Value must be in format 0x[1-9]+[0-9]* or 0x0"))
     }
  }

}
