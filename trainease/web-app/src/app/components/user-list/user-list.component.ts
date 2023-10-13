import { Component, OnInit } from '@angular/core';
import { Batch } from 'src/app/models/batch.model';
import { User, UserRole } from 'src/app/models/user.model';
import { BatchService } from 'src/app/services/batch.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  userRoles = Object.values(UserRole);
  users: User[] = [];
  totalUsersCount = 0;
  filteredUsers: User[] = [];
  selectedRole: string = '';
  selectedBatchId: string = '';
  emailId: string = '';
  batches: Batch[] = [];
  selectedBatch: string = '';
  selectedEmailId: string = '';
  selectedUserRole: string = '';
  sortDirection: number = 1;
  originalUser: User = {
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
  updateSuccess = false;
  nothingToUpdate = false;
  updateError = false;
  deleteSuccess = false;

  constructor(private userService: UserService, private batchService: BatchService) {
    this.fetchBatchIds();
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
      this.totalUsersCount = this.users.length;
    });
  }

  fetchBatchIds() {
    this.batchService.getAllBatches().subscribe((data) => {
      this.batches = data;
    });
  }

  getRoleSpecificUsers() {
    this.userService.getRoleSpecificUsers(this.selectedUserRole).subscribe((data) => {
      this.users = data;
      this.totalUsersCount = this.users.length;
    });
    this.selectedBatch = '';
    this.selectedEmailId = '';
  }

  getBatchSpecificTrainees() {
    this.userService.getBatchSpecificTrainees(this.selectedBatch).subscribe((data) => {
      this.users = data;
      this.totalUsersCount = this.users.length;
    });
    this.selectedUserRole = '';
    this.selectedEmailId = '';
  }

  getUserByEmailId() {
    this.userService.getUserByEmailId(this.selectedEmailId).subscribe((data) => {
      this.users = [];
      this.users.push(data);
      this.totalUsersCount = this.users.length;
    });
    this.selectedUserRole = '';
    this.selectedBatch = '';
  }

  deleteUser(user: User) {
    this.userService.deleteUserByEmailId(user.emailId).subscribe((data) => {
      this.deleteSuccess = true;
      setTimeout(() => {
        this.deleteSuccess = false;
      }, 2000);

      this.getAllUsers();
    });

  }

  editUser(user: User) {
    this.originalUser.emailId = user.emailId;
    this.originalUser.name = user.name;
    this.originalUser.role = user.role;
    this.originalUser.batch = user.batch;
    user.editing = true;
  }

  saveUser(user: User) {
    if (user.name === '') {

      this.updateError = true;
      this.nothingToUpdate = false;
      this.updateSuccess = false;
      setTimeout(() => {
        this.updateError = false;
      }, 2000);

    } else if (user.name === this.originalUser.name) {

      this.nothingToUpdate = true
      this.updateError = false;
      this.updateSuccess = false;
      setTimeout(() => {
        this.nothingToUpdate = false;
      }, 2000);

    }else {

      this.userService.updateUser(user).subscribe((data) => {
        user = data;
        this.updateError = false;
        this.nothingToUpdate = false;
        this.updateSuccess = true;
        setTimeout(() => {
          this.updateSuccess = false;
        }, 2000);
      });
      user.editing = false;

    }
  }

  cancelEdit(user: User) {
    user.name = this.originalUser.name;
    user.role = this.originalUser.role;
    user.batch = this.originalUser.batch;
    user.editing = false;
    this.updateError = false;
    this.updateSuccess = false;
    this.nothingToUpdate = false;
  }

  sortByName(): void {
    this.users.sort((a, b) => {
      return this.sortDirection * a.name.localeCompare(b.name);
    });
    this.sortDirection *= -1;
  }

}
