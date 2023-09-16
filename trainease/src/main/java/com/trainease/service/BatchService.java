package com.trainease.service;

import com.trainease.entity.Batch;

import java.util.List;

public interface BatchService {
    List<Batch>getAllBatches();
    Batch createBatch(Batch batch);
}
