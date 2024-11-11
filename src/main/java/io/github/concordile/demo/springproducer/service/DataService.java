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

import io.github.concordile.demo.springproducer.payload.DataRequest;
import io.github.concordile.demo.springproducer.payload.DataResponse;

import java.util.List;
import java.util.Optional;

public interface DataService {

    DataResponse insert(DataRequest data);

    Optional<DataResponse> find(String dataId);

    List<DataResponse> findAll();

    void remove(String dataId);

    void removeAll();

}
