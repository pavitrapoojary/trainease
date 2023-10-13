import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { UserService } from 'src/app/services/user.service';
import { CookieService } from 'ngx-cookie-service';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let userService: UserService;
  let cookieService: CookieService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [AuthService, CookieService, Router, UserService, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    userService = TestBed.inject(UserService);
    cookieService = TestBed.inject(CookieService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create Login Component', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize component properties', () => {
    expect(component.loginData.username).toBe('');
    expect(component.loginData.password).toBe('');
    expect(component.errorMessage).toBe('');
    expect(component.loginError).toBe(false);
    expect(component.loginWarning).toBe(false);
  });


  it('should show a warning message for empty username and password', fakeAsync(() => {
    component.loginData.username = '';
    component.loginData.password = '';
    component.onSubmit();
    tick(2000);
    expect(component.loginWarning).toBe(false);
  }));


});
