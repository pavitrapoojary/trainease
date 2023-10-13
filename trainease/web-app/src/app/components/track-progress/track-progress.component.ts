import { Component } from '@angular/core';
import { ColDef, ICellRendererParams } from 'ag-grid-community';
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
  gridApi: any;

  public columnDefs: ColDef[] = [
    {
      headerName: 'Course Name',
      field: 'course.courseName',
      cellStyle: {
        'text-align': 'center'
      }
    },
    {
      headerName: 'Duration',
      field: 'course.durationInHours',
      filter: 'agNumberColumnFilter',
      cellStyle: {
        'text-align': 'center'
      }
    },
    {
      headerName: 'Est. Start Date',
      field: 'course.estimatedStartDate',
      filter: false,
      valueFormatter: function (params) {
        const date = new Date(params.value);
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear().toString();
        return `${day}-${month}-${year}`;
      },
      cellStyle: {
        'text-align': 'center'
      }
    },
    {
      headerName: 'Est. End Date',
      field: 'course.estimatedEndDate',
      filter: false,
      valueFormatter: function (params) {
        const date = new Date(params.value);
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear().toString();
        return `${day}-${month}-${year}`;
      },
      cellStyle: {
        'text-align': 'center'
      }
    },
    {
      headerName: 'Status',
      field: 'status',
      filter: false,
      cellRenderer: this.statusCellRenderer,
      cellStyle: {
        'font-size': '25px',
        'text-align': 'center',
        
      }

    },
    {
      headerName: 'Feedback',
      field: 'feedback',
      cellStyle: {
        'text-align': 'center'
      },
      valueFormatter: function (params) {
        if (params.value) {
          return params.value;
        } else {
          return `NA`
        }
      }
    },
    {
      headerName: 'Actual Start Date',
      field: 'actualStartDate',
      filter: false,
      valueFormatter: function (params) {
        if (params.value) {
          const date = new Date(params.value);
          const day = date.getDate().toString().padStart(2, '0');
          const month = (date.getMonth() + 1).toString().padStart(2, '0');
          const year = date.getFullYear().toString();
          return `${day}-${month}-${year}`;
        } else {
          return `NA`
        }
      },
      cellStyle: {
        'text-align': 'center'
      }
    },
    {
      headerName: 'Actual End Date',
      field: 'actualEndDate',
      filter: false,
      valueFormatter: function (params) {
        if (params.value) {
          const date = new Date(params.value);
          const day = date.getDate().toString().padStart(2, '0');
          const month = (date.getMonth() + 1).toString().padStart(2, '0');
          const year = date.getFullYear().toString();
          return `${day}-${month}-${year}`;
        } else {
          return `NA`
        }
      },
      cellStyle: {
        'text-align': 'center'
      }
    }
  ];

  public defaultColDef: ColDef = {
    sortable: true,
    filter: true,
    resizable: true,
    floatingFilter: true,
  };

  constructor(private courseProgressService: ProgressService) { }

  getCoursesProgressByTraineeEmailId() {
    this.courseProgressService.getCoursesProgressByTraineeEmailId(this.selectedEmailId).subscribe((data) => {
      this.progresses = data.sort((a, b) => {
        const courseIdA = a.course.courseId.toLowerCase();
        const courseIdB = b.course.courseId.toLowerCase();
        return courseIdA.localeCompare(courseIdB, undefined, { numeric: true, sensitivity: 'base' });
      });
    });
  }

  statusCellRenderer(params: ICellRendererParams): string {
    const status = params.value;
    switch (status) {
      case 'COMPLETED':
        return `<div title="Completed">✅</div>`;
      case 'IN_PROGRESS':
        return `<div title="In Progress">⌛</div>`;
      case 'TO_BE_STARTED':
        return `<div title="To Be Started">‼️</div>`;
      default:
        return '';
    }
  }

  onGridReady(params: any) {
    this.gridApi = params.api;
  }

  exportToCSV() {
    const params = {
      fileName: this.selectedEmailId + '-progress.csv',
    };
    this.gridApi.exportDataAsCsv(params);
  }

}
