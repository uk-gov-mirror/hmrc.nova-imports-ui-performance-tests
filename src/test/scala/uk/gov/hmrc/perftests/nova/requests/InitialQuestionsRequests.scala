/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.nova.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object InitialQuestionsRequests extends BaseRequest {

  private val vehicleFromEU                         = s"$route/vehicle-from-eu-to-northern-ireland"
  private val vehicleOutsideEU                      = s"$route/vehicle-outside-eu-to-northern-ireland"
  private val areYouABusinessOrPrivateIndividual    = s"$route/business-or-private-individual"
  private val notifyingAsPurchaserOrOnBehalf        = s"$route/purchaser-or-on-behalf"
  private val purchaserABusinessOrPrivateIndividual = s"$route/purchaser-business-or-private-individual"
  private val orgVehicleForBusinessUse              = s"$route/vehicle-business-use"
  private val agentClientVehicleForBusinessUse      = s"$route/client-vehicle-business-use"

  // Initial Questions 1.0
  val navigateToVehicleFromEU: HttpRequestBuilder =
    http("Navigate to Vehicle from EU Page")
      .get(s"$baseUrl$vehicleFromEU")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Notifying for a vehicle brought into Northern Ireland").exists)

  val selectVehicleFromEUNo: HttpRequestBuilder =
    http("Select No on Vehicle from EU Page")
      .post(s"$baseUrl$vehicleFromEU")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "false")
      .check(status.is(303))

  val selectVehicleFromEUYes: HttpRequestBuilder =
    http("Select Yes on Vehicle from EU Page")
      .post(s"$baseUrl$vehicleFromEU")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "true")
      .check(status.is(303))

  // Initial Questions 1.1
  val navigateToVehicleOutsideEU: HttpRequestBuilder =
    http("Navigate to Vehicle from Outside EU Page")
      .get(s"$baseUrl$vehicleOutsideEU")
      .check(status.is(200))
      .check(regex("If you’ve brought a vehicle into Northern Ireland from outside an EU country").exists)

  // Initial Questions 2.0
  val navigateToAreYouABusinessOrPrivateIndividual: HttpRequestBuilder =
    http("Navigate to Are you a business or private individual Page")
      .get(s"$baseUrl$areYouABusinessOrPrivateIndividual")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Are you a business or private individual\\?").exists)

  val selectBusiness: HttpRequestBuilder =
    http("Select Business on Are you a business or private individual Page")
      .post(s"$baseUrl$areYouABusinessOrPrivateIndividual")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "business")
      .check(status.is(303))

  val selectPrivateIndividual: HttpRequestBuilder =
    http("Select Private Individual on Are you a business or private individual Page")
      .post(s"$baseUrl$areYouABusinessOrPrivateIndividual")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "individual")
      .check(status.is(303))

  // Initial Questions 3.0
  val navigateToNotifyingAsPurchaserOrOnBehalf: HttpRequestBuilder =
    http("Navigate to Notifying as Purchaser or On Behalf page")
      .get(s"$baseUrl$notifyingAsPurchaserOrOnBehalf")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Are you notifying as the purchaser, or on behalf of a purchaser\\?").exists)

  val selectNotifyingAsPurchaser: HttpRequestBuilder =
    http("Select Notifying as Purchaser")
      .post(s"$baseUrl$notifyingAsPurchaserOrOnBehalf")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "self")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-answers/initial-questions"))

  val selectNotifyingOnBehalf: HttpRequestBuilder =
    http("Select Notifying on Behalf of purchaser")
      .post(s"$baseUrl$notifyingAsPurchaserOrOnBehalf")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "behalfOfPurchaser")
      .check(status.is(303))
      .check(header("Location").is(purchaserABusinessOrPrivateIndividual))

  // Initial Questions 3.1
  val navigateToIsThePurchaserABusinessOrPrivateIndividual: HttpRequestBuilder =
    http("Navigate to Is the purchaser a business or private individual page")
      .get(s"$baseUrl$purchaserABusinessOrPrivateIndividual")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Is the purchaser you’re notifying on behalf of a business or private individual\\?").exists)

  val selectPurchaserIsBusiness: HttpRequestBuilder =
    http("Select Purchaser is a Business")
      .post(s"$baseUrl$purchaserABusinessOrPrivateIndividual")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "self")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-answers/initial-questions"))

  val selectPurchaserIsPrivateIndividual: HttpRequestBuilder =
    http("Select Purchaser is a Private Individual")
      .post(s"$baseUrl$purchaserABusinessOrPrivateIndividual")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "other")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-answers/initial-questions"))

  // Agent Question 1.0
  val navigateToAgentClientVehicleForBusinessUse: HttpRequestBuilder =
    http("Navigate to Has your client brought a vehicle into the UK for business use? Page")
      .get(s"$baseUrl$agentClientVehicleForBusinessUse")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Has your client brought a vehicle into the UK for business use\\?").exists)

  val selectClientVehicleForBusinessUseYes: HttpRequestBuilder =
    http("Select Yes on Has your client brought a vehicle into the UK for business use? Page")
      .post(s"$baseUrl$agentClientVehicleForBusinessUse")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "true")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-answers/initial-questions"))

  val selectClientVehicleForBusinessUseNo: HttpRequestBuilder =
    http("Select No on Has your client brought a vehicle into the UK for business use? Page")
      .post(s"$baseUrl$agentClientVehicleForBusinessUse")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "false")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-answers/initial-questions"))

  // Organisation Question 1.0
  val navigateToOrgVehicleForBusinessUse: HttpRequestBuilder =
    http("Navigate to Have you brought a vehicle into the UK for business use? Page")
      .get(s"$baseUrl$orgVehicleForBusinessUse")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Have you brought a vehicle into the UK for business use\\?").exists)

  val selectOrgVehicleForBusinessUseYes: HttpRequestBuilder =
    http("Select Yes on Have you brought a vehicle into the UK for business use? Page")
      .post(s"$baseUrl$orgVehicleForBusinessUse")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "true")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-answers/initial-questions"))

  val selectOrgVehicleForBusinessUseNo: HttpRequestBuilder =
    http("Select No on Have you brought a vehicle into the UK for business use? Page")
      .post(s"$baseUrl$orgVehicleForBusinessUse")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "false")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-answers/initial-questions"))
}
