import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { Course } from 'src/app/models/course.model';
import { BatchService } from 'src/app/services/batch.service';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.css']
})
export class CreateCourseComponent {
  displayForm = false;
  displayExcel = false;
  batches: Batch[] = [];
  selectedBatch: string = '';
  course: Course = {
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
  };

  isCourseAddedByExcel: boolean = false;
  isErrorOccurredBySavingExcel: boolean = false;
  errorMessage: string = 'Course creation error : ';
  errorMessageForm: string = 'Course creation error : ';
  isCourseAddedByForm: boolean = false;
  isErrorOccurredByForm: boolean = false;

  constructor(private courseService: CourseService, private batchService: BatchService, private http: HttpClient) { }

  ngOnInit(): void {
    this.batchService.getAllBatches().subscribe((data) => {
      this.batches = data;
    })
  }

  onSubmit(): void {
    this.course.batch.batchId = this.selectedBatch;
    this.courseService.createCourse(this.course).subscribe(
      (result) => {
        this.isCourseAddedByForm = true;
      },
      (error) => {
        this.errorMessageForm += error.error.message;
        this.isErrorOccurredByForm = true;
      });
  }

  onFileUpload(event: any) {
    const fileInput = HTMLInputElement = event.target.querySelector("#excelFile");
    if (fileInput && fileInput.files) {
      const file = fileInput.files[0];
      if (file) {
        this.courseService.uploadExcelFile(file).subscribe(
          (result) => {
            this.isCourseAddedByExcel = true;
          },
          (error) => {
            this.errorMessage += error.error.message;
            this.isErrorOccurredBySavingExcel = true;
          });
      }
    }
  }

  displayAddCourseForm() {
    this.displayExcel = false;
    this.displayForm = true;
  }

  displayExcelOption() {
    this.displayForm = false;
    this.displayExcel = true;
  }

}
