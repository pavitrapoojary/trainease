import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { Course } from 'src/app/models/course.model';
import { BatchService } from 'src/app/services/batch.service';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent {
  courses:Course[]=[];
  batches:Batch[]=[];
  selectedBatchId: string = '';
  selectedBatch: string = '';

  constructor(private courseService:CourseService,private batchService:BatchService){}

  ngOnInit():void{
    this.batchService.getAllBatches().subscribe((data)=>{
      this.batches=data;
    })
  }

  getCoursesByBatchId(selectedBatch:string):void{
    this.courseService.getCoursesByBatchId(selectedBatch).subscribe((data)=>{
      this.courses=data;
    })
  }


}
