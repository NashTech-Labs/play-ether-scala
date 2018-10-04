package factory

import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

class Web3ClientFactory(url: String) {
  def getWeb3Client(): Web3j = Web3j.build(new HttpService(url))
}

object Web3ClientFactory {
  def apply(url: String = HttpService.DEFAULT_URL): Web3ClientFactory = new Web3ClientFactory(url)
}
