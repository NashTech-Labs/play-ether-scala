package controllers

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.CSRFTokenHelper._
import play.api.test.Helpers._
import play.api.test._
import services.{IndexingService, TransferService}

class TransferControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {



  "TransferController" should {

    val publicAddress = "testaddress"
    val mockedTransferService = mock[TransferService]
    val mockedIndexingService = mock[IndexingService]
    val controller = new TransferController(stubControllerComponents(), mockedTransferService, mockedIndexingService)


    "render the transfer page from a new instance of controller" in {
      val home = controller.getTransferPage().apply(FakeRequest(GET, "/getTransfer").withFormUrlEncodedBody("csrfToken"
        -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea").withCSRFToken)
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Ether Transfer!")
      contentAsString(home) must include ("Submit details to Transfer funds!")
    }
  }
}
