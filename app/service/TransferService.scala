package service

import java.math.BigDecimal

import javax.inject.Inject
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Transfer
import org.web3j.utils.Convert

class TransferService @Inject() (web3j: Web3j) {

  def transferEther(credentials: Credentials, toAccount: String, amount: BigDecimal ): TransactionReceipt = {
    Transfer.sendFunds(web3j, credentials, toAccount, amount, Convert.Unit.ETHER).send
  }
}