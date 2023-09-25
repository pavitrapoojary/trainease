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
  }

  constructor(private userService: UserService, private batchService: BatchService) {
    this.fetchBatchIds();
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data;
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
    });
    this.selectedBatch = '';
    this.selectedEmailId = '';
  }

  getBatchSpecificTrainees() {
    this.userService.getBatchSpecificTrainees(this.selectedBatch).subscribe((data) => {
      this.users = data;
    });
    this.selectedUserRole = '';
    this.selectedEmailId = '';
  }

  getUserByEmailId() {
    this.userService.getUserByEmailId(this.selectedEmailId).subscribe((data) => {
      this.users = [];
      this.users.push(data);
    });
    this.selectedUserRole = '';
    this.selectedBatch = '';
  }

  deleteUser(user: User) {
    this.userService.deleteUserByEmailId(user.emailId).subscribe((data) => {
      console.log(data);
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
    if(user.role === UserRole.TRAINEE){
      user.batch.batchId = this.selectedBatch;
    }
    this.userService.updateUser(user).subscribe((data) => {
      user = data;
    });
    user.editing = false;
  }

  cancelEdit(user: User) {
    user.name = this.originalUser.name;
    user.role = this.originalUser.role;
    user.batch = this.originalUser.batch;
    user.editing = false;
  }

  sortByName(): void {
    this.users.sort((a, b) => {
      return this.sortDirection * a.name.localeCompare(b.name);
    });
    this.sortDirection *= -1;
  }

}
