import { Component } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { User, UserRole } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent {
  loggedInUserEmail = localStorage.getItem('username') || '';
  loggedInUserBatch: Batch = {
    batchId: '',
    batchName: '',
    batchDescription: '',
    editing: false
  };
  loggedInUserProfile: User = {
    emailId: '',
    name: '',
    role: UserRole.TRAINEE,
    batch: this.loggedInUserBatch,
    editing: false
  };

  originalUserProfile: User = {
    emailId: '',
    name: '',
    role: UserRole.TRAINEE,
    batch: this.loggedInUserBatch,
    editing: false
  };
  updateSuccess = false;
  updateError = false;
  nothingToUpdate = false;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUserProfile(this.loggedInUserEmail).subscribe((data) => {
      this.loggedInUserProfile = data;
    });
  }

  editUser(user: User) {
    this.updateError = false;
    this.updateSuccess = false;
    this.nothingToUpdate = false;
    this.originalUserProfile.emailId = user.emailId;
    this.originalUserProfile.name = user.name;
    this.originalUserProfile.role = user.role;
    if (this.originalUserProfile.role === 'TRAINEE') {
      this.originalUserProfile.batch.batchId = user.batch.batchId;
      this.originalUserProfile.batch.batchName = user.batch.batchName;
      this.originalUserProfile.batch.batchDescription = user.batch.batchDescription;
    }
    this.loggedInUserProfile.editing = true;
  }

  saveUser(user: User) {
    if (user.name === '') {
      this.updateError = true;
      this.nothingToUpdate = false;
      this.updateSuccess = false;
      setTimeout(() => {
        this.updateError = false;
      }, 2000);
    } else if (user.name === this.originalUserProfile.name) {
      this.nothingToUpdate = true
      this.updateError = false;
      this.updateSuccess = false;
      setTimeout(() => {
        this.nothingToUpdate = false;
      }, 2000);
    }
    else {
      this.userService.updateUser(user).subscribe((data) => {
        this.loggedInUserProfile = data;
        this.loggedInUserProfile.editing = false;
        this.updateError = false;
        this.nothingToUpdate = false;
        this.updateSuccess = true;
        setTimeout(() => {
          this.updateSuccess = false;
        }, 2000);
      });
    }

  }

  cancelEdit(user: User) {
    this.loggedInUserProfile.name = this.originalUserProfile.name;
    this.loggedInUserProfile.editing = false;
    this.updateError = false;
    this.updateSuccess = false;
    this.nothingToUpdate = false;
  }



}
