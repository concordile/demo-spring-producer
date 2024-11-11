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

import io.github.concordile.demo.springproducer.domain.DataDomain;
import io.github.concordile.demo.springproducer.payload.DataResponse;
import io.github.concordile.demo.springproducer.converter.DataDomain2ResponseConverter;
import io.github.concordile.demo.springproducer.converter.DataRequest2DomainConverter;
import io.github.concordile.demo.springproducer.payload.DataRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final Map<String, DataDomain> dataById = new ConcurrentHashMap<>();

    private final DataRequest2DomainConverter dataRequest2DomainConverter;
    private final DataDomain2ResponseConverter dataDomain2ResponseConverter;

    @Override
    public DataResponse insert(DataRequest data) {
        DataDomain domain = dataRequest2DomainConverter.convert(data);
        dataById.put(domain.getId(), domain);
        return dataDomain2ResponseConverter.convert(domain);
    }

    @Override
    public Optional<DataResponse> find(String dataId) {
        return Optional.ofNullable(dataById.get(dataId))
                .map(dataDomain2ResponseConverter::convert);
    }

    @Override
    public List<DataResponse> findAll() {
        return dataById.values().stream()
                .map(dataDomain2ResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(String dataId) {
        dataById.remove(dataId);
    }

    @Override
    public void removeAll() {
        dataById.clear();
    }

}
