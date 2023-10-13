import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccountComponent } from './my-account.component';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { SideNavBarComponent } from '../side-nav-bar/side-nav-bar.component';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatIcon } from '@angular/material/icon';

describe('MyAccountComponent', () => {
  let component: MyAccountComponent;
  let fixture: ComponentFixture<MyAccountComponent>;
  let router : Router;
  let userService : UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyAccountComponent,SideNavBarComponent,MatIcon],
      imports: [FormsModule, RouterTestingModule.withRoutes([]), HttpClientModule],
      providers: [UserService, Router, HttpClient]
    }).compileComponents();

    fixture = TestBed.createComponent(MyAccountComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    userService = TestBed.inject(UserService);
    fixture.detectChanges();
  });

  it('should create My Account Component', () => {
    expect(component).toBeTruthy();
  });
});
