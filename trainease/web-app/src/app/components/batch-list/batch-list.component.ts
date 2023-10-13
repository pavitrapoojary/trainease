import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { BatchService } from 'src/app/services/batch.service';

@Component({
  selector: 'app-batch-list',
  templateUrl: './batch-list.component.html',
  styleUrls: ['./batch-list.component.css']
})
export class BatchListComponent {
  batches: Batch[] = [];
  totalBatchesCount = 0;
  originalBatch: Batch = {
    batchId: '',
    batchName: '',
    batchDescription: '',
    editing: false
  };

  updateSuccess = false;
  nothingToUpdate = false;
  updateError = false;

  constructor(private batchService: BatchService) { }

  ngOnInit(): void {
    this.batchService.getAllBatches().subscribe((data) => {
      this.batches = data;
      this.totalBatchesCount = this.batches.length;
    });
  }

  editBatch(batch: Batch) {
    this.updateError = false;
    this.updateSuccess = false;
    this.nothingToUpdate = false;
    this.originalBatch.batchId = batch.batchId;
    this.originalBatch.batchName = batch.batchName;
    this.originalBatch.batchDescription = batch.batchDescription;
    batch.editing = true;
  }

  saveBatch(batch: Batch) {
    if (batch.batchName === '' || batch.batchDescription === '') {
      
      this.updateError = true;
      this.nothingToUpdate = false;
      this.updateSuccess = false;
      setTimeout(() => {
        this.updateError = false;
      }, 2000);
      
    } else if (batch.batchName === this.originalBatch.batchName && batch.batchDescription == this.originalBatch.batchDescription) {
      
      this.nothingToUpdate = true
      this.updateError = false;
      this.updateSuccess = false;
      setTimeout(() => {
        this.nothingToUpdate = false;
      }, 2000);

    }
    else {

      this.batchService.updateBatch(batch).subscribe((data) => {
        batch = data;
        this.updateError = false;
        this.nothingToUpdate = false;
        this.updateSuccess = true;
        setTimeout(() => {
          this.updateSuccess = false;
        }, 2000);
      });
      batch.editing = false;

    }
  }

  cancelEdit(batch: Batch) {
    batch.batchName = this.originalBatch.batchName;
    batch.batchDescription = this.originalBatch.batchDescription;
    batch.editing = false;
    this.updateError = false;
    this.updateSuccess = false;
    this.nothingToUpdate = false;
  }
}
