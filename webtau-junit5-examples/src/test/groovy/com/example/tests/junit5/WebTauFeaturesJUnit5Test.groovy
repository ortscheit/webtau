/*
 * Copyright 2020 webtau maintainers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tests.junit5

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testingisdocumenting.webtau.featuretesting.WebTauBrowserFeaturesTestData
import org.testingisdocumenting.webtau.featuretesting.WebTauRestFeaturesTestData
import org.testingisdocumenting.webtau.http.testserver.FixedResponsesHandler
import org.testingisdocumenting.webtau.http.testserver.TestServer

class WebTauFeaturesJUnit5Test {
    private static final FixedResponsesHandler responseHandler = new FixedResponsesHandler()

    private static final TestServer testServer = new TestServer(responseHandler)

    private static final JUnit5FeatureTestRunner testRunner = new JUnit5FeatureTestRunner()

    @BeforeAll
    static void startServer() {
        testServer.startRandomPort()
        WebTauRestFeaturesTestData.registerEndPoints(testServer, responseHandler)
        WebTauBrowserFeaturesTestData.registerEndPoints(responseHandler)
    }

    @AfterAll
    static void stopServer() {
        testServer.stop()
    }

    @Test
    void weatherGroovyTest() {
        testRunner.runAndValidate(WeatherGroovyTest, testServer.uri.toString())
    }

    @Test
    void weatherJavaTest() {
        testRunner.runAndValidate(WeatherJavaTest, testServer.uri.toString())
    }

    @Test
    void searchByQuery() {
        testRunner.runAndValidate(WebSearchJavaTest, testServer.uri.toString())
    }
}
