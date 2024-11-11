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

package io.github.concordile.demo.springproducer.service;

import io.github.concordile.demo.springproducer.payload.DataResponse;
import io.github.concordile.demo.springproducer.payload.DataRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DataServiceImplTest {

    @BeforeEach
    void setUp(@Autowired DataService service) {
        service.removeAll();
    }

    @Test
    void should_insert(@Autowired DataService service) {
        DataRequest request = DataRequest.builder()
                .id("new-id")
                .data("new-value")
                .build();
        DataResponse response = service.insert(request);
        assertEquals(DataResponse.builder()
                .id("new-id")
                .data("new-value")
                .build(), response);
    }

    @Test
    void should_find(@Autowired DataService service) {
        String dataId = "id";
        DataRequest request = DataRequest.builder()
                .id(dataId)
                .data("value")
                .build();
        service.insert(request);
        DataResponse response = service.find(dataId).orElseThrow();
        assertEquals(DataResponse.builder()
                .id("id")
                .data("value")
                .build(), response);
    }

    @Test
    void should_findAll(@Autowired DataService service) {
        DataRequest request1 = DataRequest.builder()
                .id("id1")
                .data("value1")
                .build();
        service.insert(request1);
        DataRequest request2 = DataRequest.builder()
                .id("id2")
                .data("value2")
                .build();
        service.insert(request2);
        List<DataResponse> responses = service.findAll();
        assertEquals(Set.of(
                        DataResponse.builder()
                                .id("id1")
                                .data("value1")
                                .build(),
                        DataResponse.builder()
                                .id("id2")
                                .data("value2")
                                .build()),
                new HashSet<>(responses));
    }

    @Test
    void should_remove(@Autowired DataService service) {
        String dataId = "id";
        DataRequest request = DataRequest.builder()
                .id(dataId)
                .data("value")
                .build();
        service.insert(request);
        assertTrue(service.find(dataId).isPresent());
        service.remove(dataId);
        assertFalse(service.find(dataId).isPresent());
    }

}