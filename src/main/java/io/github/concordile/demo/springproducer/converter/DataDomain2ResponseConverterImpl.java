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
import io.github.concordile.demo.springproducer.payload.DataResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DataDomain2ResponseConverterImpl implements DataDomain2ResponseConverter {

    @NonNull
    @Override
    public DataResponse convert(DataDomain source) {
        return DataResponse.builder()
                .id(source.getId())
                .data(source.getValue())
                .build();
    }

}
