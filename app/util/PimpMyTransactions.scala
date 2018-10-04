package util

import org.web3j.protocol.core.methods.response.TransactionReceipt

object PimpMyTransactions {

  implicit class TransactionReceiptFormatter(tr: TransactionReceipt) {
    def toJSON =
      s"""
        |                {
        |                "transactionHash": "${tr.getTransactionHash}",
        |                "transactionIndex": "${tr.getTransactionIndex}",
        |                "blockNumber": "${tr.getBlockNumber}",
        |                "blockHash": "${tr.getBlockHash}",
        |                "cumulativeGasUsed":"${tr.getCumulativeGasUsed}",
        |                "gasUsed":"${tr.getGasUsed}",
        |                "contractAddress":"${tr.getContractAddress}",
        |                "root":"${tr.getRoot}",
        |                "status":"${tr.getStatus}",
        |                "from":"${tr.getFrom}",
        |                "to":"${tr.getTo}"
        |                }
      """.stripMargin
  }

}
