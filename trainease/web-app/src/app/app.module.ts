import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { SideNavBarComponent } from './components/side-nav-bar/side-nav-bar.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AddUserComponent } from './components/add-user/add-user.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateBatchComponent } from './components/create-batch/create-batch.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { BatchListComponent } from './components/batch-list/batch-list.component';
import { CourseListComponent } from './components/course-list/course-list.component';
import { TrackProgressComponent } from './components/track-progress/track-progress.component';
import { UpdateProgressComponent } from './components/update-progress/update-progress.component';
import { CreateCourseComponent } from './components/create-course/create-course.component';
import { AgGridModule } from 'ag-grid-angular';
import { LoginComponent } from './components/login/login.component';
import { NgChartsModule } from 'ng2-charts';
import { AuthInterceptor } from './services/auth.interceptor';
import { MyAccountComponent } from './components/my-account/my-account.component';
import { TrackBatchProgressComponent } from './components/track-batch-progress/track-batch-progress.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SideNavBarComponent,
    UserListComponent,
    AddUserComponent,
    CreateBatchComponent,
    BatchListComponent,
    CourseListComponent,
    TrackProgressComponent,
    UpdateProgressComponent,
    CreateCourseComponent,
    LoginComponent,
    MyAccountComponent,
    TrackBatchProgressComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    AgGridModule,
    NgChartsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule
  ],
  providers: [ 
    {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  }, 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
