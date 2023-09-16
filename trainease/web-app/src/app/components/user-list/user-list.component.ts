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

  constructor(private userService: UserService, private batchService: BatchService) {
    this.fetchBatchIds();
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers(){
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
    this.selectedBatch='';
    this.selectedEmailId='';
  }

  getBatchSpecificTrainees() {
    this.userService.getBatchSpecificTrainees(this.selectedBatch).subscribe((data) => {
      this.users = data;
    });
    this.selectedUserRole='';
    this.selectedEmailId='';
  }

  getUserByEmailId() {
    this.userService.getUserByEmailId(this.selectedEmailId).subscribe((data) => {
      this.users = [];
      this.users.push(data);
    });
    this.selectedUserRole='';
    this.selectedBatch='';
  }

  applyFilters(): void {
    // Apply filters based on selectedRole and selectedBatchId
    this.filteredUsers = this.users.filter((user) => {
      const roleMatch =
        !this.selectedRole || user.role === this.selectedRole;
      const batchIdMatch =
        !this.selectedBatchId || user.batchId.includes(this.selectedBatchId);

      return roleMatch && batchIdMatch;
    });
  }

  deleteUser(emailId:string) {
    this.userService.deleteUserByEmailId(emailId).subscribe((data)=>{
      console.log(data);
      this.getAllUsers();
    });
    
  }

  editUser() { }

}
