package com.trainease.service;

import com.trainease.entity.Batch;
import com.trainease.repository.BatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BatchServiceImpl implements BatchService{
    private BatchRepository batchRepository;
    @Override
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    @Override
    public Batch createBatch(Batch batch) {
        return batchRepository.save(batch);
    }

}
