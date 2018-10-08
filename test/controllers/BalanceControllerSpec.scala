package controllers

import java.math.BigInteger

import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.CSRFTokenHelper._
import play.api.test.Helpers._
import play.api.test._
import services.BalanceService
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class BalanceControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  val publicAddress = "testaddress"

  "BalanceController" should {

    val mockedBalanceService = mock[BalanceService]
    val controller = new BalanceController(stubControllerComponents(), mockedBalanceService)

    "render the balance page from a new instance of controller" in {
      val balance = controller.getBalancePage().apply(FakeRequest(GET, "/").withFormUrlEncodedBody("csrfToken"
        -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea").withCSRFToken)
      status(balance) mustBe OK
      contentType(balance) mustBe Some("text/html")
      contentAsString(balance) must include ("Welcome to Ether Balance!")
      contentAsString(balance) must include ("Submit an address to get blance")
    }


    "handle get balnace request and render the balance for valid address" in {
      when(mockedBalanceService.getBalance(publicAddress)).thenReturn(Future{BigInt(0)})
      val balance = controller.getBalance().apply(FakeRequest(POST, "/getBalance").withFormUrlEncodedBody("csrfToken"
        -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "publicAddress" -> publicAddress).withCSRFToken)
      status(balance) mustBe OK
      contentType(balance) mustBe Some("text/html")
      contentAsString(balance) must include ("Welcome to Ether Balance!")
      contentAsString(balance) must include ("Ether Available: 0")
    }
//
//    "handle get balnace request and render the balance for invalid address" in {
//      when(mockedBalanceService.getBalance("invalidaddress")).thenReturn(null)
//      val balance = controller.getBalance().apply(FakeRequest(POST, "/getBalance").withFormUrlEncodedBody("csrfToken"
//        -> "9c48f081724087b31fcf6099b7eaf6a276834cd9-1487743474314-cda043ddc3d791dc500e66ea", "publicAddress" -> "invalidaddress").withCSRFToken)
//      status(balance) mustBe OK
//      contentType(balance) mustBe Some("text/html")
//      contentAsString(balance) must include ("Welcome to Ether Balance!")
//      contentAsString(balance) must include ("Invalid Address!!")
//    }
  }
}
