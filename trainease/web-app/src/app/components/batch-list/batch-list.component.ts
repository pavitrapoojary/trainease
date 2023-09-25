import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { BatchService } from 'src/app/services/batch.service';

@Component({
  selector: 'app-batch-list',
  templateUrl: './batch-list.component.html',
  styleUrls: ['./batch-list.component.css']
})
export class BatchListComponent {
  batches: Batch[] = []
  originalBatch: Batch = {
    batchId: '',
    batchName: '',
    batchDescription: '',
    editing: false
  }

  constructor(private batchService: BatchService) { }

  ngOnInit(): void {
    this.batchService.getAllBatches().subscribe((data) => {
      this.batches = data;
    });
  }

  editBatch(batch: Batch) {
    this.originalBatch.batchId = batch.batchId;
    this.originalBatch.batchName = batch.batchName;
    this.originalBatch.batchDescription = batch.batchDescription;
    batch.editing = true;
  }

  saveBatch(batch: Batch) {
    this.batchService.updateBatch(batch).subscribe((data) => {
      batch = data;
    });
    batch.editing = false;
  }

  cancelEdit(batch: Batch) {
    batch.batchName = this.originalBatch.batchName;
    batch.batchDescription = this.originalBatch.batchDescription;
    batch.editing = false;
  }
}
