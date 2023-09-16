import { Component } from '@angular/core';
import { User, UserRole } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  user: User = {
    emailId: '',
    name: '',
    role: UserRole.ADMIN, // Set the default role as ADMIN
    batchId: '', // Initialize batchId as empty
  };

  constructor(private userService: UserService) {}

  onSubmit(): void {
    // Send the user data to your UserService to create the user
    this.userService.addUser(this.user).subscribe((result) => {
      // Handle the result or navigate to another page
      console.log(result+": user added");
    });
  }

}
