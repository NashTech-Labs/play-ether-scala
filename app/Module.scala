import com.google.inject.AbstractModule
import factory.{ElasticClientFactory, Web3ClientFactory}
import net.codingwell.scalaguice.ScalaModule
import org.elasticsearch.client.transport.TransportClient
import org.web3j.protocol.Web3j

class Module extends AbstractModule with ScalaModule {
import util.ConfigProvider.web3j

  override def configure(): Unit = {
    bind[Web3j].toInstance(web3j)
    bind[TransportClient].toInstance(ElasticClientFactory.getESClient)
  }

}
