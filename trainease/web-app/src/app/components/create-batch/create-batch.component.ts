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
    editing: false
  };
  isBatchCreated: boolean = false;
  isBatchCreateError: boolean = false;
  isBatchEmpty: boolean = false;
  originalErrorMessage = 'Batch creation error : ';
  errorMessage: string = 'Batch creation error : ';

  constructor(private batchService: BatchService) { }

  onSubmit(): void {
    if (this.batch.batchName === '' ||
      this.batch.batchId === '' ||
      this.batch.batchDescription === '') {
      this.isBatchEmpty = true;
      setTimeout(() => {
        this.isBatchEmpty = false;
      }, 2000);
    } else {
      this.batchService.createBatch(this.batch).subscribe(
        (result) => {
          this.isBatchCreated = true;
          setTimeout(() => {
            this.isBatchCreated = false;
          }, 2000);
        },
        (error) => {
          this.errorMessage += error.error.message;
          this.isBatchCreateError = true;
          setTimeout(() => {
            this.isBatchCreateError = false;
          }, 2000);
          
        });
        this.errorMessage = '' + this.originalErrorMessage;
    }
  }

}
