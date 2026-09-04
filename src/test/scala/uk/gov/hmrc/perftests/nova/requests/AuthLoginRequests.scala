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

object AuthLoginRequests extends BaseRequest {

  val navigateToAuth: HttpRequestBuilder =
    http("Auth Wizard")
      .get(authLoginStubUrl)
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Authority Wizard").exists)

  val authLogInAsIndividual: HttpRequestBuilder =
    http("Login as Individual User")
      .post(authLoginStubUrl)
      .formParam("redirectionUrl", s"$baseUrl$route")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("authorityId", "")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Individual")
      .formParam("enrolment[0].name", "")
      .formParam("enrolment[0].taxIdentifier[0].name", "")
      .formParam("enrolment[0].taxIdentifier[0].value", "")
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(303))
      .check(header("Location").is(s"$baseUrl$route"))

  val authLogInAsOrganisation: HttpRequestBuilder =
    http("Login as Organisation User")
      .post(authLoginStubUrl)
      .formParam("redirectionUrl", s"$baseUrl$route")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("authorityId", "")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("enrolment[0].name", "")
      .formParam("enrolment[0].taxIdentifier[0].name", "")
      .formParam("enrolment[0].taxIdentifier[0].value", "")
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(303))
      .check(header("Location").is(s"$baseUrl$route"))

  val authLogInAsAgent: HttpRequestBuilder =
    http("Login as Agent User")
      .post(authLoginStubUrl)
      .formParam("redirectionUrl", s"$baseUrl$route")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("authorityId", "")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Agent")
      .formParam("enrolment[0].name", "")
      .formParam("enrolment[0].taxIdentifier[0].name", "")
      .formParam("enrolment[0].taxIdentifier[0].value", "")
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(303))
      .check(header("Location").is(s"$baseUrl$route"))

  val authLogInAsOrganisationVRN: HttpRequestBuilder =
    http("Login as VRN-registered Organisation User")
      .post(authLoginStubUrl)
      .formParam("redirectionUrl", s"$baseUrl$route")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("authorityId", "400000003")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("enrolment[0].name", "HMRC-MTD-VAT")
      .formParam("enrolment[0].taxIdentifier[0].name", "VRN")
      .formParam("enrolment[0].taxIdentifier[0].value", "740000003")
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(303))
      .check(header("Location").is(s"$baseUrl$route"))

  val authLogInAsOrganisationVAT: HttpRequestBuilder =
    http("Login as VAT-registered Organisation User")
      .post(authLoginStubUrl)
      .formParam("redirectionUrl", s"$baseUrl$route")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("authorityId", "500000003")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("enrolment[0].name", "HMCE-VATDEC-ORG")
      .formParam("enrolment[0].taxIdentifier[0].name", "VATRegNo")
      .formParam("enrolment[0].taxIdentifier[0].value", "750000003")
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(303))
      .check(header("Location").is(s"$baseUrl$route"))

  val authLogInAsAgentVAT: HttpRequestBuilder =
    http("Login as VAT-registered Agent User")
      .post(authLoginStubUrl)
      .formParam("redirectionUrl", s"$baseUrl$route")
      .formParam("csrfToken", csrfTokenExpr)
      .formParam("authorityId", "600000002")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Agent")
      .formParam("enrolment[0].name", "HMCE-VAT-AGNT")
      .formParam("enrolment[0].taxIdentifier[0].name", "AgentRefNo")
      .formParam("enrolment[0].taxIdentifier[0].value", "AA6002")
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(303))
      .check(header("Location").is(s"$baseUrl$route"))
}
