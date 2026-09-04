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

package uk.gov.hmrc.perftests.nova.simulation

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.nova.requests.InitialQuestionsRequests._
import uk.gov.hmrc.perftests.nova.requests.StartNotificationRequests._
import uk.gov.hmrc.perftests.nova.requests.AuthLoginRequests._

trait IndividualUserSimulation extends PerformanceTestRunner {

  setup(
    "individual-importing-from-outside-eu-journey",
    "Private individual notifying vehicle import from outside EU"
  ) withRequests (
    navigateToAuth,
    authLogInAsIndividual,
    navigateToNovaEntryPoint,
    navigateToLandingPage,
    navigateToBeforeYouContinuePage,
    navigateFromBeforeYouContinuePage,
    navigateToVehicleFromEU,
    selectVehicleFromEUNo,
    navigateToVehicleOutsideEU
  )

  setup(
    "individual-notifying-on-behalf-of-business",
    "Private individual notifying on behalf of a business purchaser"
  ) withRequests (
    navigateToAuth,
    authLogInAsIndividual,
    navigateToNovaEntryPoint,
    navigateToLandingPage,
    navigateToBeforeYouContinuePage,
    navigateFromBeforeYouContinuePage,
    navigateToVehicleFromEU,
    selectVehicleFromEUYes,
    navigateToAreYouABusinessOrPrivateIndividual,
    selectBusiness,
    navigateToNotifyingAsPurchaserOrOnBehalf,
    selectNotifyingOnBehalf,
    navigateToIsThePurchaserABusinessOrPrivateIndividual,
    selectPurchaserIsBusiness
  )
}
