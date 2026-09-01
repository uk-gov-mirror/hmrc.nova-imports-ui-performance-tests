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

  private val vehicleFromEUPage                      = s"$route/vehicle-from-eu-to-northern-ireland"
  private val vehicleOutsideEUPage                   = s"$route/vehicle-outside-eu-to-northern-ireland"
  private val NotifyingAsBusinessOrPrivateIndividual = s"$route/business-or-private-individual"

  val getVehicleFromEUPage: HttpRequestBuilder =
    http("Navigate to Vehicle from EU page")
      .get(s"$baseUrl$vehicleFromEUPage")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Notifying for a vehicle brought into Northern Ireland").exists)

  val postVehicleFromEUPageAsNo: HttpRequestBuilder =
    http("Submit No on Vehicle from EU page")
      .post(s"$baseUrl$vehicleFromEUPage")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "false")
      .check(status.is(303))
      .check(header("Location").is(vehicleOutsideEUPage))

  val postVehicleFromEUPageAsYes: HttpRequestBuilder =
    http("Submit Yes on Vehicle from EU page")
      .post(s"$baseUrl$vehicleFromEUPage")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("value", "true")
      .check(status.is(303))
      .check(header("Location").is(NotifyingAsBusinessOrPrivateIndividual))

  val getVehicleOutsideEUPage: HttpRequestBuilder =
    http("Navigate to Vehicle from Outside EU page")
      .get(s"$baseUrl$vehicleOutsideEUPage")
      .check(status.is(200))
      .check(regex("If you’ve brought a vehicle into Northern Ireland from outside an EU country").exists)
}
