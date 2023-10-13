import { Component } from '@angular/core';
import { CourseProgress, Status } from 'src/app/models/course-progress.model';
import { Course } from 'src/app/models/course.model';
import { UserRole } from 'src/app/models/user.model';
import { ProgressService } from 'src/app/services/progress.service';


@Component({
  selector: 'app-update-progress',
  templateUrl: './update-progress.component.html',
  styleUrls: ['./update-progress.component.css']
})
export class UpdateProgressComponent {
  loggedInUserEmail = localStorage.getItem('username') || '';
  statuses = Object.values(Status);
  courses: Course[] = [];
  allCoursesProgress : CourseProgress[] = [];
  courseProgresses : CourseProgress[] = [];
  totalCoursesCount = 0;
  originalCourseProgress: CourseProgress = {
    progressId: 0,
    user: {
      emailId: '',
      name: '',
      role: UserRole.TRAINEE,
      batch: {
        batchId: '',
        batchName: '',
        batchDescription: '',
        editing: false
      },
      editing:false
    },
    batch: {
      batchId: '',
      batchName: '',
      batchDescription: '',
      editing: false
    },
    course: {
      courseId: '',
      batch: {
        batchId: '',
        batchName: '',
        batchDescription: '',
        editing: false
      },
      courseName: '',
      description: '',
      link: '',
      durationInHours: 0,
      estimatedStartDate: new Date(),
      estimatedEndDate: new Date(),
      subjectMatterExpert: '',
      editing:false
    },
    status: Status.TO_BE_STARTED,
    feedback: '',
    actualStartDate: new Date,
    actualEndDate: new Date,
    editing:false
  };
  options = [
    {id: 'option1', value: 'ALL', label: 'ALL'},
    {id: 'option2', value: 'TO_BE_STARTED', label: 'TO_BE_STARTED'},
    {id: 'option3', value: 'IN_PROGRESS', label: 'IN_PROGRESS'},
    {id: 'option4', value: 'COMPLETED', label: 'COMPLETED'}
  ];

  selectedValue: string = 'ALL';

  constructor(private progressService: ProgressService) { }

  ngOnInit(): void {
    this.progressService.getCoursesProgressByTraineeEmailId(this.loggedInUserEmail).subscribe((data) => {
      this.courseProgresses = data.sort((a, b) => {
        const courseIdA = a.course.courseId.toLowerCase();
        const courseIdB = b.course.courseId.toLowerCase();
        return courseIdA.localeCompare(courseIdB, undefined, { numeric: true, sensitivity: 'base' });
      });
      this.allCoursesProgress = data.sort((a, b) => {
        const courseIdA = a.course.courseId.toLowerCase();
        const courseIdB = b.course.courseId.toLowerCase();
        return courseIdA.localeCompare(courseIdB, undefined, { numeric: true, sensitivity: 'base' });
      });
      this.totalCoursesCount = this.courseProgresses.length;
    });
  }

  editCourseProgress(courseProgress:CourseProgress){
    this.originalCourseProgress.status = courseProgress.status;
    this.originalCourseProgress.actualStartDate = courseProgress.actualStartDate;
    this.originalCourseProgress.actualEndDate = courseProgress.actualEndDate;
    this.originalCourseProgress.feedback = courseProgress.feedback;
    courseProgress.editing = true;
  }

  saveCourseProgress(courseProgress:CourseProgress){
    this.progressService.updateCourseProgress(courseProgress).subscribe((data)=>{
      courseProgress = data;
    });
    courseProgress.editing = false;
    this.onRadioChange(this.selectedValue);
  }

  cancelEdit(courseProgress:CourseProgress){
    courseProgress.status = this.originalCourseProgress.status ;
    courseProgress.actualStartDate = this.originalCourseProgress.actualStartDate  ;
    courseProgress.actualEndDate = this.originalCourseProgress.actualEndDate ;
    courseProgress.feedback = this.originalCourseProgress.feedback ;
    courseProgress.editing = false;
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

  // getActualDateTextColor(estDate: Date, actualDate: Date): string {
  //   if (actualDate > estDate) {
  //     return 'red';
  //   } else {
  //     return 'rgb(21, 158, 0)';
  //   }
  // }

  onRadioChange(value:string) {
    console.log(value);
    if(value==='ALL'){
      this.courseProgresses = this.allCoursesProgress.filter(courseProgress => 
        courseProgress.status==='TO_BE_STARTED' || courseProgress.status==='IN_PROGRESS' || courseProgress.status==='COMPLETED');
    }else{
      this.courseProgresses = this.allCoursesProgress.filter(courseProgress => courseProgress.status===value);
    }
    this.totalCoursesCount = this.courseProgresses.length;
  }

}
