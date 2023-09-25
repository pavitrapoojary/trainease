package com.trainease.service;

import com.trainease.entity.Batch;
import com.trainease.repository.BatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void getBatchByBatchIdWhenBatchExists() {
        String batchId = "B1";
        Batch expectedBatch = Batch.builder().batchId(batchId).batchName("B1 batch").build();
        when(batchRepository.findById(batchId)).thenReturn(Optional.ofNullable(expectedBatch));
        Batch actualBatch = batchService.getBatchByBatchId(batchId);
        assertNotNull(actualBatch);
        assertEquals(expectedBatch.getBatchId(), actualBatch.getBatchId());
        assertEquals(expectedBatch.getBatchName(), actualBatch.getBatchName());
        verify(batchRepository).findById(batchId);
    }

    @Test
    void getBatchByBatchIdWhenBatchDoesNotExist() {
        String batchId = "B1";
        when(batchRepository.findById(batchId)).thenReturn(Optional.empty());
        Batch actualBatch = batchService.getBatchByBatchId(batchId);
        assertNull(actualBatch);
    }

    @Test
    void createBatchWhenBatchIdDoesNotExist() {
        Batch newBatch = Batch.builder().batchId("B1").batchName("Bname").build();
        when(batchRepository.save(newBatch)).thenReturn(newBatch);
        Batch createdBatch = batchService.createBatch(newBatch);
        assertNotNull(createdBatch);
        assertEquals(newBatch.getBatchId(), createdBatch.getBatchId());
        assertEquals(newBatch.getBatchName(), createdBatch.getBatchName());
        verify(batchRepository).save(newBatch);
    }

    @Test
    void createBatchWhenBatchIdExists() {
        Batch existingBatch = Batch.builder().batchId("B1").batchName("Bname").build();
        when(batchRepository.existsById("B1")).thenReturn(Boolean.TRUE);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            batchService.createBatch(existingBatch);
        });
        assertEquals("Batch already exists with the given batch ID.", exception.getMessage());
        verify(batchRepository).existsById(existingBatch.getBatchId());
        verify(batchRepository, never()).save(any(Batch.class));
    }

    @Test
    void updateBatchWhenBatchExists() {
        Batch existsBatch = Batch.builder().batchId("B1").build();
        when(batchRepository.findById("B1")).thenReturn(Optional.ofNullable(existsBatch));
        when(batchRepository.save(existsBatch)).thenReturn(existsBatch);
        Batch updatedBatch = batchService.updateBatch(existsBatch);
        assertNotNull(updatedBatch);
    }

    @Test
    void updateBatchWhenBatchIdIsNull() {
        Batch nullBatch = Batch.builder().batchId(null).build();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            batchService.updateBatch(nullBatch);
        });
        assertEquals("Invalid batch data or batchId.", exception.getMessage());
        verify(batchRepository, never()).save(any(Batch.class));
    }

    @Test
    void updateBatchWhenBatchDoesNotExist() {
        Batch batchDoesNotExist = Batch.builder().batchId("B1").build();
        when(batchRepository.findById("B1")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            batchService.updateBatch(batchDoesNotExist);
        });
        assertEquals("Batch not found.", exception.getMessage());
        verify(batchRepository).findById(batchDoesNotExist.getBatchId());
        verify(batchRepository, never()).save(any(Batch.class));
    }
}
