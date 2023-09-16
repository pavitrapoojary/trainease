import { Component } from '@angular/core';
import { User, UserRole } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  user: User = {
    emailId: '',
    name: '',
    role: UserRole.ADMIN,
    batchId: '',
  };
  userRoles = Object.values(UserRole);

  constructor(private userService: UserService) { }

  onSubmit(): void {
    this.userService.addUser(this.user).subscribe((result) => {
      console.log(result + ": user added");
    });
  }

}
