import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { BatchService } from 'src/app/services/batch.service';

@Component({
  selector: 'app-create-batch',
  templateUrl: './create-batch.component.html',
  styleUrls: ['./create-batch.component.css']
})
export class CreateBatchComponent {
  batch: Batch = {
    batchId: '',
    batchName: '',
    batchDescription: '',
    editing:false
  };
  isBatchCreated: boolean = false;
  isBatchCreateError: boolean = false;
  errorMessage: string = 'Batch creation error : ';

  constructor(private batchService: BatchService) { }

  onSubmit(): void {
    this.batchService.createBatch(this.batch).subscribe(
      (result) => {
        console.log(JSON.stringify(result) + ": batch added");
        this.isBatchCreated = true;
      },
      (error) => {
        console.error("Error occurred while creating a training batch:", error);
        this.errorMessage += error.error.message;
        this.isBatchCreateError = true;
      })
  }

}
