package com.trainease.service;

import com.trainease.entity.Batch;
import com.trainease.repository.BatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchServiceImplTest {

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private BatchServiceImpl batchService;

    @Test
    void getAllBatches() {
        Batch batch1 = Batch.builder().batchId("B1").build();
        Batch batch2 = Batch.builder().batchId("B2").build();
        Batch batch3 = Batch.builder().batchId("B3").build();
        List<Batch> expectedBatchList = Arrays.asList(batch1, batch2, batch3);
        when(batchRepository.findAll()).thenReturn(expectedBatchList);
        List<Batch> actualBatchList = batchService.getAllBatches();
        assertArrayEquals(expectedBatchList.toArray(), actualBatchList.toArray());
        verify(batchRepository).findAll();
    }

    @Test
    void createBatch() {
        Batch newBatch = Batch.builder().batchId("B1").batchName("Bname").build();
        when(batchRepository.save(newBatch)).thenReturn(newBatch);
        Batch createdBatch = batchService.createBatch(newBatch);
        assertNotNull(createdBatch);
        assertEquals(newBatch.getBatchId(), createdBatch.getBatchId());
        assertEquals(newBatch.getBatchName(), createdBatch.getBatchName());
        verify(batchRepository).save(newBatch);
    }
}
