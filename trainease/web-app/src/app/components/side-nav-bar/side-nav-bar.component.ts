import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Batch } from 'src/app/models/batch.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-side-nav-bar',
  templateUrl: './side-nav-bar.component.html',
  styleUrls: ['./side-nav-bar.component.css']
})
export class SideNavBarComponent {
  loggedInUserRole: string | null = '';

  batch: Batch = {
    batchId: '',
    batchName: '',
    batchDescription: '',
    editing: false
  };

  constructor(private cookie: CookieService, private router: Router,private userService:UserService) { }

  ngOnInit(): void {
    this.loggedInUserRole = localStorage.getItem('role');
  }


  logout() {
    this.cookie.delete("Bearer");
    this.cookie.delete("username");
    this.cookie.delete("password");
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    localStorage.removeItem('batch');
    this.router.navigate(['']);
  }
}
