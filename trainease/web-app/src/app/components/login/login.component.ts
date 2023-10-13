import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = {
    username: '',
    password: ''
  };
  errorMessage = '';
  token: any;
  loginError = false;
  loginWarning = false;

  constructor(private cookie: CookieService, private authService: AuthService, private router: Router, private userService: UserService) { }

  onSubmit() {
    if (this.loginData.username === '' || this.loginData.password === '') {
      this.loginWarning = true;
      setTimeout(() => {
        this.loginWarning = false;
      }, 2000);

    } else {
      this.authService.login(this.loginData.username, this.loginData.password).subscribe(
        (response) => {
          this.token = response;
          this.cookie.set("Bearer", this.token.jwt);
          localStorage.setItem('username', this.loginData.username);
          this.getUserData(this.loginData.username);
        },
        (error) => {
          this.loginError = true;
          setTimeout(() => {
            this.loginError = false;
          }, 2000);
        });
    }
  }

  async getUserData(userEmail: string) {
    try {
      let data = await this.userService.getUserRoleAndBatch(userEmail).toPromise();
      localStorage.setItem('role', data!.role);
      if (data?.role === 'TRAINEE') {
        localStorage.setItem('batch', data!.batch.batchId);
      }
      this.router.navigate(['home']);
    } catch (error) {
      console.error(error);
    }
  }
}
