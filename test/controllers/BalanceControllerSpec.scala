package controllers

import java.math.BigInteger

import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.CSRFTokenHelper._
import play.api.test.Helpers._
import play.api.test._
import service.BalanceService

class BalanceControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  val publicAddress = "testaddress"

  "BalanceController" should {

    "render the balance page from a new instance of controller" in {
      val mockedBalanceService = mock[BalanceService]
      val controller = new BalanceController(stubControllerComponents(), mockedBalanceService)
      val home = controller.getBalancePage().apply(FakeRequest(GET, "/").withFormUrlEncodedBody("csrfToken"
        -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea").withCSRFToken)
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Ether Balance!")
      contentAsString(home) must include ("Submit an address to get blance")
    }


    "handle get balnace request and render the balance for valid address" in {
      val mockedBalanceService = mock[BalanceService]
      when(mockedBalanceService.getBalance("testaddress")).thenReturn(BigInteger.valueOf(0))
      val controller = new BalanceController(stubControllerComponents(), mockedBalanceService)
      val home = controller.getBalance().apply(FakeRequest(POST, "/getBalance").withFormUrlEncodedBody("csrfToken"
        -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "publicAddress" -> "testaddress").withCSRFToken)
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Ether Balance!")
      contentAsString(home) must include ("Ether Available: 0")
    }

    "handle get balnace request and render the balance for invalid address" in {
      val mockedBalanceService = mock[BalanceService]
      when(mockedBalanceService.getBalance("invalidaddress")).thenReturn(null)
      val controller = new BalanceController(stubControllerComponents(), mockedBalanceService)
      val home = controller.getBalance().apply(FakeRequest(POST, "/getBalance").withFormUrlEncodedBody("csrfToken"
        -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "publicAddress" -> "invalidaddress").withCSRFToken)
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Ether Balance!")
      contentAsString(home) must include ("Invalid Address!!")
    }

  }
}
