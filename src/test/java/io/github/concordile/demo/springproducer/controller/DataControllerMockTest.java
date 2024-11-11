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

package io.github.concordile.demo.springproducer.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.concordile.demo.springproducer.service.DataService;
import io.github.concordile.demo.springproducer.payload.DataRequest;
import io.github.concordile.demo.springproducer.payload.DataResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DataController.class)
class DataControllerMockTest {

    static final String API_PREFIX = "/api/datum";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DataService dataService;

    @Test
    void should_create_data() throws Exception {
        // mock
        String dataId = "test-id";
        String dataValue = "test-value";
        when(dataService.insert(eq(DataRequest.builder()
                .id(dataId)
                .data(dataValue)
                .build()))).thenReturn(DataResponse.builder()
                .id(dataId)
                .data(dataValue)
                .build());

        // perform
        String body = mockMvc.perform(post(API_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DataRequest.builder()
                                .id(dataId)
                                .data(dataValue)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // assert
        DataResponse response = objectMapper.readValue(body, DataResponse.class);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getId()).isEqualTo(dataId);
            softly.assertThat(response.getData()).isEqualTo(dataValue);
        });
    }

    @Test
    void should_return_data() throws Exception {
        // mock
        String dataId = "test-id";
        String dataValue = "test-value";
        when(dataService.find(dataId)).thenReturn(Optional.of(DataResponse.builder()
                .id(dataId)
                .data(dataValue)
                .build()));

        // perform
        String body = mockMvc.perform(get(API_PREFIX + "/{dataId}", dataId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // assert
        DataResponse response = objectMapper.readValue(body, DataResponse.class);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getId()).isEqualTo(dataId);
            softly.assertThat(response.getData()).isEqualTo(dataValue);
        });
    }

    @Test
    void should_return_datum() throws Exception {
        // mock
        String dataId = "test-id";
        String dataValue = "test-value";
        when(dataService.findAll()).thenReturn(List.of(DataResponse.builder()
                .id(dataId)
                .data(dataValue)
                .build()));

        // perform
        String body = mockMvc.perform(get(API_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // assert
        List<DataResponse> responses = objectMapper.readValue(body, new TypeReference<>() {
        });
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(responses).hasSize(1);
            softly.assertThat(responses.get(0).getId()).isEqualTo(dataId);
            softly.assertThat(responses.get(0).getData()).isEqualTo(dataValue);
        });
    }

    @Test
    void should_delete_data() throws Exception {
        // mock
        String dataId = "test-id";
        doNothing().when(dataService).remove(eq(dataId));

        // perform
        mockMvc.perform(delete(API_PREFIX + "/{dataId}", dataId))
                .andExpect(status().isOk());

        // verify
        verify(dataService).remove(eq(dataId));
    }

}