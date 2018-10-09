package services

import javax.inject.Inject
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.xcontent.XContentType

class IndexingService @Inject()(transportClient: TransportClient) {

  def createJSON(indexName: String, indexType: String, id: Option[String], source: String): IndexResponse = {
    transportClient.prepareIndex(indexName, indexType, id.orNull)
      .setSource(source, XContentType.JSON).get()
  }
}
