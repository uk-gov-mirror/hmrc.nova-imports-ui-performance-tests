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

object StartNotificationRequests extends BaseRequest {

  private val landingPage           = s"$route/notification-of-vehicle-arrivals"
  private val beforeYouContinuePage = s"$route/before-you-continue"

  val getNovaEntryPoint: HttpRequestBuilder =
    http("Navigate to NoVA service")
      .get(s"$baseUrl$route")
      .check(status.is(303))
      .check(header("Location").is(landingPage))

  val getLandingPage: HttpRequestBuilder =
    http("Navigate to NoVA landing Page")
      .get(s"$baseUrl$landingPage")
      .check(status.is(200))
      .check(regex("Notification of Vehicle Arrivals \\(NOVA\\)").exists)

  val getBeforeYouContinuePage: HttpRequestBuilder =
    http("Navigate to Before you continue Page")
      .get(s"$baseUrl$beforeYouContinuePage")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Before you continue").exists)

  val postBeforeYouContinuePage: HttpRequestBuilder =
    http("Continue from Before you continue Page")
      .post(s"$baseUrl$beforeYouContinuePage")
      .formParam("csrfToken", csrfTokenExpr)
      .check(status.is(303))
      .check(header("Location").is(s"$route/vehicle-from-eu-to-northern-ireland"))

}
