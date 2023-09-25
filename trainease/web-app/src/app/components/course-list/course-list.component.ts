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
  courses: Course[] = [];
  batches: Batch[] = [];
  selectedBatch: string = '';
  originalCourse:Course={
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
    estimatedStartDate: new Date,
    estimatedEndDate: new Date,
    subjectMatterExpert: '',
    editing: false
  }

  constructor(private courseService: CourseService, private batchService: BatchService) { }

  ngOnInit(): void {
    this.batchService.getAllBatches().subscribe((data) => {
      this.batches = data;
    })
  }

  getCoursesByBatchId(selectedBatch: string): void {
    this.courseService.getCoursesByBatchId(selectedBatch).subscribe((data) => {
      this.courses = data;
    });
  }

  editCourse(course: Course) {
    this.originalCourse.courseId = course.courseId;
    this.originalCourse.batch = course.batch;
    this.originalCourse.courseName = course.courseName;
    this.originalCourse.description = course.description;
    this.originalCourse.durationInHours = course.durationInHours;
    this.originalCourse.link = course.link;
    this.originalCourse.estimatedStartDate = course.estimatedStartDate;
    this.originalCourse.estimatedEndDate = course.estimatedEndDate;
    this.originalCourse.subjectMatterExpert = course.subjectMatterExpert;
    course.editing = true;
  }

  saveCourse(course: Course) {
    this.courseService.updateCourse(course).subscribe((data)=>{
      course = data;
    });
    course.editing = false;
  }

  cancelEdit(course: Course) {
    course.courseId = this.originalCourse.courseId ;
    course.batch = this.originalCourse.batch ;
    course.courseName = this.originalCourse.courseName ;
    course.description = this.originalCourse.description ;
    course.durationInHours = this.originalCourse.durationInHours ;
    course.link = this.originalCourse.link;
    course.estimatedStartDate = this.originalCourse.estimatedStartDate ;
    course.estimatedEndDate = this.originalCourse.estimatedEndDate ;
    course.subjectMatterExpert = this.originalCourse.subjectMatterExpert;
    course.editing = false;
  }


}
