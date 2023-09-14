import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit{
  users: User[] =[];
  filteredUsers: User[] = [];
  selectedRole: string = '';
  selectedBatchId: string = '';
  
  constructor(private userService: UserService){}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((data)=>{
      this.users = data;
      this.applyFilters();
    });
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

}
