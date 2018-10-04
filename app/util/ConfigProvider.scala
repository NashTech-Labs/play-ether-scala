package util

import com.typesafe.config.{Config, ConfigFactory}
import factory.Web3ClientFactory
import org.web3j.crypto.{Credentials, WalletUtils}
import org.web3j.protocol.Web3j

object ConfigProvider {
  lazy val config:Config = ConfigFactory.load("application.conf").getConfig("transfer")
  lazy val WALLET_PASSWORD: String = System.getProperty("password")
  lazy val WALLET_SOURCE: String = config.getString("walletsource")
  lazy val credentials: Credentials = WalletUtils.loadCredentials("use_your_own", WALLET_SOURCE)
  lazy val INFURA_CLIENT: String = config.getString("infura")
  lazy val ACCOUNT_TO: String = config.getString("accountto")
  lazy val ACCOUNT_FROM: String = credentials.getAddress
  lazy val web3j: Web3j = Web3ClientFactory(INFURA_CLIENT).getWeb3Client()
}