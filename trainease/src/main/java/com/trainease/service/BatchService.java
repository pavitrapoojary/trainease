package com.trainease.service;

import com.trainease.entity.Batch;

import java.util.List;

public interface BatchService {
    List<Batch> getAllBatches();

    Batch getBatchByBatchId(String batchId);

    Batch createBatch(Batch batch);

    Batch updateBatch(Batch batch);

}
