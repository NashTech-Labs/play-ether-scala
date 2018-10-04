package factory

import java.net.InetAddress

import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient

object ElasticClientFactory {

  def getESClient: TransportClient = new PreBuiltTransportClient(Settings.builder().put("cluster.name", "etheana").build())
    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300))
}

