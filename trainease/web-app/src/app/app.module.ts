import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { SideNavBarComponent } from './components/side-nav-bar/side-nav-bar.component';
import { FooterComponent } from './components/footer/footer.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { HttpClientModule } from '@angular/common/http';
import { AddUserComponent } from './components/add-user/add-user.component';
import { FormsModule } from '@angular/forms';
import { CreateBatchComponent } from './components/create-batch/create-batch.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { BatchListComponent } from './components/batch-list/batch-list.component';
import { CourseListComponent } from './components/course-list/course-list.component';
import { TrackProgressComponent } from './components/track-progress/track-progress.component';
import { UpdateProgressComponent } from './components/update-progress/update-progress.component';
import { CreateCourseComponent } from './components/create-course/create-course.component';
import { AgGridModule } from 'ag-grid-angular';
import { TestCompComponent } from './components/test-comp/test-comp.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SideNavBarComponent,
    FooterComponent,
    UserListComponent,
    AddUserComponent,
    CreateBatchComponent,
    BatchListComponent,
    CourseListComponent,
    TrackProgressComponent,
    UpdateProgressComponent,
    CreateCourseComponent,
    TestCompComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    AgGridModule,
  ],
  providers: [  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
