import { Batch } from "./batch.model";
import { Course } from "./course.model";
import { User } from "./user.model";

export interface CourseProgress {
    progressId:number;
    user:User;
    batch:Batch;
    course:Course;
    status:Status;
    feedback:string;
    actualStartDate:Date;
    actualEndDate:Date;
    editing:boolean;
}

export enum Status {
    TO_BE_STARTED = 'TO_BE_STARTED',
    IN_PROGRESS = 'IN_PROGRESS',
    COMPLETED = 'COMPLETED'
}