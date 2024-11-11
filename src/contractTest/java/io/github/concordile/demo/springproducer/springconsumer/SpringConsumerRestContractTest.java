/*
 * Copyright 2023-2024 The Concordile Authors
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

package io.github.concordile.demo.springproducer.springconsumer;

import io.github.concordile.demo.springproducer.payload.DataRequest;
import io.github.concordile.demo.springproducer.payload.DataResponse;
import io.github.concordile.demo.springproducer.service.DataService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@DirtiesContext
@AutoConfigureMessageVerifier
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class SpringConsumerRestContractTest {

    @MockBean
    DataService dataService;

    @BeforeEach
    public void setupWebApplicationContext(@Autowired WebApplicationContext context) {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @BeforeEach
    public void mockDataService() {
        when(dataService.insert(argThat(
                request -> request.getId().matches("id-.+")
                        && request.getData().matches("value-.+"))))
                .then(invocation -> {
                    DataRequest request = invocation.getArgument(0, DataRequest.class);
                    return DataResponse.builder()
                            .id(request.getId())
                            .data(request.getData())
                            .build();
                });
    }

}
