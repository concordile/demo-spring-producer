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

package io.github.concordile.demo.springproducer.converter;

import io.github.concordile.demo.springproducer.domain.DataDomain;
import io.github.concordile.demo.springproducer.payload.DataRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DataRequest2DomainConverterImpl.class)
class DataRequest2DomainConverterImplTest {

    @Test
    void should_convert(@Autowired DataRequest2DomainConverter converter) {
        DataDomain domain = converter.convert(DataRequest.builder()
                .id("test-id")
                .data("test-value")
                .build());
        assertEquals(DataDomain.builder()
                .id("test-id")
                .value("test-value")
                .build(), domain);
    }

}