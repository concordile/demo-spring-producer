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

import io.github.concordile.demo.springproducer.payload.DataRequest;
import io.github.concordile.demo.springproducer.payload.DataResponse;
import io.github.concordile.demo.springproducer.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/datum")
public class DataController {

    private final DataService dataService;

    @PostMapping
    public ResponseEntity<DataResponse> create(@RequestBody DataRequest data) {
        return ResponseEntity.ok(dataService.insert(data));
    }

    @GetMapping("/{dataId}")
    public ResponseEntity<DataResponse> get(@PathVariable String dataId) {
        return dataService.find(dataId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DataResponse>> getAll() {
        return ResponseEntity.ok(dataService.findAll());
    }

    @DeleteMapping("/{dataId}")
    public ResponseEntity<?> delete(@PathVariable String dataId) {
        dataService.remove(dataId);
        return ResponseEntity.ok().build();
    }

}
