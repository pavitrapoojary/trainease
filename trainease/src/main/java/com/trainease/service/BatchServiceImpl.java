package com.trainease.service;

import com.trainease.entity.Batch;
import com.trainease.repository.BatchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BatchServiceImpl implements BatchService {
    private BatchRepository batchRepository;

    @Override
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    @Override
    public Batch getBatchByBatchId(String batchId) {
        return batchRepository.findById(batchId).orElse(null);
    }

    @Override
    public Batch createBatch(Batch batch) {
        if (batchRepository.existsById(batch.getBatchId())) {
            throw new IllegalArgumentException("Batch already exists with the given batch ID.");
        }
        return batchRepository.save(batch);
    }

    @Override
    public Batch updateBatch(Batch batch) {
        if (batch == null || batch.getBatchId() == null) {
            throw new IllegalArgumentException("Invalid batch data or batchId.");
        }

        Optional<Batch> batchExistsOptional = batchRepository.findById(batch.getBatchId());
        if (batchExistsOptional.isPresent()) {
            Batch batchExists = batchExistsOptional.get();
            batchExists.setBatchName(batch.getBatchName());
            batchExists.setBatchDescription(batch.getBatchDescription());
            return batchRepository.save(batchExists);
        }
        throw new EntityNotFoundException("Batch not found.");
    }

}
