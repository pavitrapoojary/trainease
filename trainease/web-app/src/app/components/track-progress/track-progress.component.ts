import { Component } from '@angular/core';
import { CourseProgress } from 'src/app/models/course-progress.model';
import { ProgressService } from 'src/app/services/progress.service';

@Component({
  selector: 'app-track-progress',
  templateUrl: './track-progress.component.html',
  styleUrls: ['./track-progress.component.css']
})
export class TrackProgressComponent {

  progresses:CourseProgress[]=[];
  selectedEmailId:string='';
  
  constructor(private courseProgressService:ProgressService){}

  getCoursesProgressByTraineeEmailId(){
    this.courseProgressService.getCoursesProgressByTraineeEmailId(this.selectedEmailId).subscribe((data)=>{
      this.progresses=data;
    });
  }

}
