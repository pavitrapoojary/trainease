package com.trainease.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainease.entity.Batch;
import com.trainease.service.BatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class BatchControllerTest {
    @Mock
    BatchService batchService;

    @InjectMocks
    BatchController batchController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
    }

    @Test
    void testGetAllBatches() throws Exception {
        Batch batch1 = Batch.builder()
                .batchId("B1")
                .batchName("B1_name").build();
        Batch batch2 = Batch.builder()
                .batchId("B2")
                .batchName("B2_name").build();

        List<Batch> batchList = Arrays.asList(batch1, batch2);

        when(batchService.getAllBatches()).thenReturn(batchList);

        mockMvc.perform(get("/batches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].batchName").value(batch1.getBatchName()))
                .andExpect(jsonPath("$[1].batchName").value(batch2.getBatchName()));

        verify(batchService, times(1)).getAllBatches();
    }

    @Test
    void testCreateBatch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Batch newBatch = Batch.builder()
                .batchId("B1")
                .batchName("B1_name").build();

        when(batchService.createBatch(any(Batch.class))).thenReturn(newBatch);

        mockMvc.perform(post("/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBatch)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.batchName").value(newBatch.getBatchName()));

        verify(batchService, times(1)).createBatch(any(Batch.class));
    }

    @Test
    void testUpdateBatch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Batch updatedBatch = Batch.builder()
                .batchId("B1")
                .batchName("Updated Batch").build();

        when(batchService.updateBatch(any(Batch.class))).thenReturn(updatedBatch);

        mockMvc.perform(put("/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBatch)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.batchName").value(updatedBatch.getBatchName()));

        verify(batchService, times(1)).updateBatch(any(Batch.class));
    }


}