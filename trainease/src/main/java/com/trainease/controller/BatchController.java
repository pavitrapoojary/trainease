package com.trainease.controller;

import com.trainease.entity.Batch;
import com.trainease.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BatchController {
    @Autowired
    BatchService batchService;

    @GetMapping("/batches")
    public List<Batch> getAllBatches() {
        return this.batchService.getAllBatches();
    }

    @PostMapping("/batches")
    public Batch createBatch(@RequestBody Batch batch) {
        return this.batchService.createBatch(batch);
    }

    @PutMapping("/batches")
    public Batch updateBatch(@RequestBody Batch batch) {
        return this.batchService.updateBatch(batch);
    }

}
