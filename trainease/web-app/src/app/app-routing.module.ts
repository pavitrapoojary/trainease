import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { CreateBatchComponent } from './components/create-batch/create-batch.component';
import { BatchListComponent } from './components/batch-list/batch-list.component';
import { CourseListComponent } from './components/course-list/course-list.component';
import { TrackProgressComponent } from './components/track-progress/track-progress.component';
import { CreateCourseComponent } from './components/create-course/create-course.component';
import { UpdateProgressComponent } from './components/update-progress/update-progress.component';
import { LoginComponent } from './components/login/login.component';
import { MyAccountComponent } from './components/my-account/my-account.component';
import { TrackBatchProgressComponent } from './components/track-batch-progress/track-batch-progress.component';

const routes: Routes = [
  {path:"",component:LoginComponent},
  {path:"home",component:HomeComponent},
  {path:"user-list",component:UserListComponent},
  {path:"add-user",component:AddUserComponent},
  {path:"create-batch",component:CreateBatchComponent},
  {path:"batch-list",component:BatchListComponent},
  {path:"course-list",component:CourseListComponent},
  {path:"track-progress",component:TrackProgressComponent},
  {path:"create-course",component:CreateCourseComponent},
  {path:"update-progress",component:UpdateProgressComponent},
  {path:'my-account',component:MyAccountComponent},
  {path:'track-batch-progress',component:TrackBatchProgressComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
