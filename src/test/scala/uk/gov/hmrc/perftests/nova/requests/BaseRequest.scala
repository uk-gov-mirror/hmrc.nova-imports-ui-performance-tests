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
import io.gatling.core.check.CheckBuilder
import io.gatling.core.check.regex.RegexCheckType
import uk.gov.hmrc.performance.conf.ServicesConfiguration

trait BaseRequest extends ServicesConfiguration {

  val authUrl: String          = baseUrlFor("auth-login-stub")
  val authLoginStubUrl: String = s"$authUrl/auth-login-stub/gg-sign-in"
  val baseUrl: String          = baseUrlFor("nova-imports-notification-frontend")
  val route: String            = "/nova-imports"

  val CsrfPattern = """<input type="hidden" name="csrfToken" value="([^"]+)""""

  protected def saveCsrfToken(): CheckBuilder[RegexCheckType, String] =
    regex(_ => CsrfPattern).saveAs("csrfToken")

  protected val csrfTokenExpr: String = "#{csrfToken}"

}
