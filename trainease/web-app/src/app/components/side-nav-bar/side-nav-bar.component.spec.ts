import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideNavBarComponent } from './side-nav-bar.component';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from 'src/app/services/user.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('SideNavBarComponent', () => {
  let component: SideNavBarComponent;
  let fixture: ComponentFixture<SideNavBarComponent>;
  let router : Router;
  let cookieService : CookieService;
  let userService : UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SideNavBarComponent],
      imports: [RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [UserService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(SideNavBarComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    cookieService = TestBed.inject(CookieService);
    userService = TestBed.inject(UserService);
    fixture.detectChanges();
  });

  it('should create Side nav bar component', () => {
    expect(component).toBeTruthy();
  });
});
