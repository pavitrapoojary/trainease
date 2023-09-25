import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { User, UserRole } from 'src/app/models/user.model';
import { BatchService } from 'src/app/services/batch.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  batches: Batch[] = [];
  selectedBatch: string = '';
  user: User = {
    emailId: '',
    name: '',
    role: UserRole.ADMIN,
    batch: {
      batchId: '',
      batchName: '',
      batchDescription: '',
      editing: false
    },
    editing: false
  };
  userRoles = Object.values(UserRole);
  isUserAddedByExcel: boolean = false;
  isErrorOccurredBySavingExcel: boolean = false;
  errorMessage: string = 'User creation error : ';
  errorMessageForm: string = 'User creation error : ';
  isUserAddedByForm: boolean = false;
  isErrorOccurredByForm: boolean = false;

  constructor(private userService: UserService, private batchService: BatchService) { }

  ngOnInit(): void {
    this.batchService.getAllBatches().subscribe((data) => {
      this.batches = data;
    })
  }

  onSubmit(): void {
    this.user.batch.batchId = this.selectedBatch;
    this.userService.addUser(this.user).subscribe(
      (result) => {
        console.log(result + ": user added");
        this.isUserAddedByForm = true;
      },
      (error) => {
        console.error("Error occurred while creating new user", error);
        this.errorMessageForm += error.error.message;
        this.isErrorOccurredByForm = true;
      });
  }

  onFileUpload(event: any) {
    const fileInput = HTMLInputElement = event.target.querySelector("#excelFileUsers");
    if (fileInput && fileInput.files) {
      const file = fileInput.files[0];
      if (file) {
        this.userService.uploadExcelFile(file).subscribe(
          (result) => {
            console.log("Users created from the excel file.", result);
            this.isUserAddedByExcel = true;
          },
          (error) => {
            console.error("Error occurred while uploading the file:", error);
            this.errorMessage += error.error.message;
            this.isErrorOccurredBySavingExcel = true;

          }
        );
      }
    }
  }


}
