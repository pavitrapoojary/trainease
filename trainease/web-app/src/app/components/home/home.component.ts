import { Component } from '@angular/core';
import Chart from 'chart.js/auto';
import { Batch } from 'src/app/models/batch.model';
import { CourseWiseStatus } from 'src/app/models/course-wise-status.model';
import { Course } from 'src/app/models/course.model';
import { BatchService } from 'src/app/services/batch.service';
import { CourseService } from 'src/app/services/course.service';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  loggedInUserEmail = localStorage.getItem('username') || '';
  loggedInUserRole = localStorage.getItem('role') || '';
  courses: Course[] = [];
  batches: Batch[] = [];
  courseWiseStatus: CourseWiseStatus = {
    toBeStartedCount: 0,
    inProgressCount: 0,
    completedCount: 0
  };
  feedbackList: string[] = [];
  selectedBatch: string = '';
  selectedBatchDescription: string = '';
  selectedCourse: string = '';
  courseWiseStatusChart: any;
  totalCoursesCount: number = 0;
  totalTraineesCount: number = 0;

  selectedBatchId: string = '';
  courseStatusChart: any;
  courseStatus: CourseWiseStatus = {
    toBeStartedCount: 0,
    inProgressCount: 0,
    completedCount: 0
  };

  constructor(private courseService: CourseService, private batchService: BatchService, private reportService: ReportService) { }

  ngOnInit(): void {
    if(this.loggedInUserRole==='TRAINEE'){
      this.getTraineeReport(this.loggedInUserEmail);
    }else{
      this.batchService.getAllBatches().subscribe((data) => {
        this.batches = data;
      });
    }
  }

  getBatchWiseReport(selectedBatch: string): void {
    this.reportService.getTotalCoursesInBatch(this.selectedBatch).subscribe((data) => {
      this.totalCoursesCount = data;
    });

    this.reportService.getTotalTraineesInBatch(this.selectedBatch).subscribe((data) => {
      this.totalTraineesCount = data;
    });

    this.findBatchDescriptionById();

    this.courseService.getCoursesByBatchId(selectedBatch).subscribe((data) => {
      this.courses = data.sort((a, b) => {
        const courseIdA = a.courseId.toLowerCase();
        const courseIdB = b.courseId.toLowerCase();
        return courseIdA.localeCompare(courseIdB, undefined, { numeric: true, sensitivity: 'base' });
      });
    });
  }

  findBatchDescriptionById(): void {
    const selectedBatchEntity = this.batches.find(batch => batch.batchId === this.selectedBatch);
    if (selectedBatchEntity) {
      this.selectedBatchDescription = selectedBatchEntity.batchDescription;
    } else {
      this.selectedBatchDescription = 'Batch not found';
    }
  }

  async getCourseWiseReport(selectedCourse: string) {
    try {
      let data = await this.reportService.getCourseIdWiseStatusCountForAdmin(selectedCourse).toPromise();
      this.courseWiseStatus = data!;
      this.createChart(this.courseWiseStatus);
      this.getFeedbackListByCourseId();
    } catch (error) {
      console.error(error);
    }

  }

  createChart(courseWiseStatus: CourseWiseStatus) {
    if (this.courseWiseStatusChart) {
      this.courseWiseStatusChart.destroy();
    }
    this.courseWiseStatusChart = new Chart("courseWiseStatusChart", {
      type: 'pie',
      data: {
        labels: ['To Be Started', 'In Progress', 'Completed'],
        datasets: [{
          label: 'Trainee Count',
          data: [courseWiseStatus.toBeStartedCount, courseWiseStatus.inProgressCount, courseWiseStatus.completedCount],
          backgroundColor: [
            'rgba(59, 59, 57, 0.8)',
            'rgba(252, 252, 3, 0.8)',
            'rgba(21, 158, 0,0.8)'
          ],
          borderColor: [
            'rgba(10, 10, 10, 1)',
            'rgba(224, 224, 11, 1)',
            'rgba(14, 105, 0, 1)'
          ],
          borderWidth: 1
        }],
      },
      options: {
        aspectRatio: 2.5,
        plugins: {
          legend: {
            position: 'bottom',
          },
          tooltip: {
            callbacks: {
              label: function (context) {
                const value = context.parsed || 0;
                const total = context.dataset.data.reduce((acc, curr) => acc + curr, 0);
                const percentage = ((value / total) * 100).toFixed(2) + '%';
                return `Trainee Count : ${value} (${percentage})`;
              },
            },
          },
        },
      },
    });
  }

  getFeedbackListByCourseId(): void {
    this.reportService.getFeedbackListByCourseId(this.selectedCourse).subscribe((data) => {
      this.feedbackList = data;
    });
  }



  async getTraineeReport(selectedEmailId: string) {
    try {
      let batchData = await this.reportService.getTraineeBatchId(selectedEmailId).toPromise();
      this.selectedBatchId = batchData!;
      this.getTotalCoursesInBatch(this.selectedBatchId, selectedEmailId);
    } catch (error) {
      console.error(error);
    }
  }

  getTotalCoursesInBatch(batchId: string, emailId: string) {
    this.reportService.getTotalCoursesInBatch(batchId).subscribe((data) => {
      this.totalCoursesCount = data;
    });

    this.getCourseStatusReport(emailId);
  }

  async getCourseStatusReport(selectedEmailId: string) {
    try {
      let data = await this.reportService.getTotalCoursesStatusCountForTrainee(selectedEmailId).toPromise();
      this.courseStatus = data!;
      this.createChartTrainee(this.courseStatus);
    } catch (error) {
      console.error(error);
    }
  }

  createChartTrainee(courseStatus: CourseWiseStatus) {
    if (this.courseStatusChart) {
      this.courseStatusChart.destroy();
    }
    this.courseStatusChart = new Chart("courseStatusChart", {
      type: 'pie',
      data: {
        labels: ['To Be Started', 'In Progress', 'Completed'],
        datasets: [{
          label: 'Trainee Count',
          data: [courseStatus.toBeStartedCount, courseStatus.inProgressCount, courseStatus.completedCount],
          backgroundColor: [
            'rgba(59, 59, 57, 0.8)',
            'rgba(252, 252, 3, 0.8)',
            'rgba(21, 158, 0,0.8)'
          ],
          borderColor: [
            'rgba(10, 10, 10, 1)',
            'rgba(224, 224, 11, 1)',
            'rgba(14, 105, 0, 1)'
          ],
          borderWidth: 1
        }],
      },
      options: {
        aspectRatio: 2.5,
        plugins: {
          legend: {
            position: 'bottom',
          },
          tooltip: {
            callbacks: {
              label: function (context) {
                const value = context.parsed || 0;
                const total = context.dataset.data.reduce((acc, curr) => acc + curr, 0);
                const percentage = ((value / total) * 100).toFixed(2) + '%';
                return `Course Count : ${value} (${percentage})`;
              },
            },
          },
        },
      },
    });
  }

}
