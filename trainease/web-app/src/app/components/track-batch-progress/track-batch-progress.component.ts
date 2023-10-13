import { Component } from '@angular/core';
import { BatchProgress } from 'src/app/models/batch-progress.model';
import { Batch } from 'src/app/models/batch.model';
import { BatchService } from 'src/app/services/batch.service';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-track-batch-progress',
  templateUrl: './track-batch-progress.component.html',
  styleUrls: ['./track-batch-progress.component.css']
})
export class TrackBatchProgressComponent {
  selectedBatch: string = '';
  batchProgressList: BatchProgress[] = [];
  batches: Batch[] = [];
  showTable = false;
  totalTraineesCount = 0;
  totalCoursesCount = 0;

  constructor(private reportService: ReportService, private batchService: BatchService) {
    this.fetchBatchIds();
  }

  ngOnInit(): void {
  }

  fetchBatchIds() {
    this.batchService.getAllBatches().subscribe((data) => {
      this.batches = data;
    });
  }

  getBatchProgress() {
    this.reportService.getBatchProgress(this.selectedBatch).subscribe((data: any) => {
      this.batchProgressList = data;
      this.showTable = true;
      this.totalTraineesCount = this.batchProgressList.length;
      if(this.totalTraineesCount>0){
        this.totalCoursesCount = this.batchProgressList[0].courseProgressStatusList.length;
      }else{
        this.totalCoursesCount = 0 ;
      }
    });

  }

}
