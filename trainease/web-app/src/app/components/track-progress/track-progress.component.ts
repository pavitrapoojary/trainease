import { Component } from '@angular/core';
import { CourseProgress } from 'src/app/models/course-progress.model';
import { ProgressService } from 'src/app/services/progress.service';

@Component({
  selector: 'app-track-progress',
  templateUrl: './track-progress.component.html',
  styleUrls: ['./track-progress.component.css']
})
export class TrackProgressComponent {

  progresses: CourseProgress[] = [];
  selectedEmailId: string = '';

  constructor(private courseProgressService: ProgressService) { }

  getCoursesProgressByTraineeEmailId() {
    this.courseProgressService.getCoursesProgressByTraineeEmailId(this.selectedEmailId).subscribe((data) => {
      this.progresses = data;
    });
  }

  getStatusBgColor(status: string): string {
    switch (status) {
      case 'COMPLETED':
        return 'rgb(21, 158, 0)';
      case 'IN_PROGRESS':
        return 'rgb(252, 252, 3)';
      case 'TO_BE_STARTED':
        return 'black';
      default:
        return '';
    }
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'COMPLETED':
        return 'white';
      case 'IN_PROGRESS':
        return 'black';
      case 'TO_BE_STARTED':
        return 'white';
      default:
        return '';
    }
  }

  getActualDateTextColor(estDate: Date, actualDate: Date): string {
    if (actualDate > estDate) {
      return 'red';
    } else {
      return 'rgb(21, 158, 0)';
    }
  }

}
