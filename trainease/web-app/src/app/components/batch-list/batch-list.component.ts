import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { BatchService } from 'src/app/services/batch.service';

@Component({
  selector: 'app-batch-list',
  templateUrl: './batch-list.component.html',
  styleUrls: ['./batch-list.component.css']
})
export class BatchListComponent {
  batches:Batch[]=[]

  constructor(private batchService:BatchService){}

  ngOnInit(): void {
    this.batchService.getAllBatches().subscribe((data)=>{
      this.batches = data;
    });
  }
}
