import { SME } from "./sme.model";

export interface CourseProgress {
    id:string;
    emailId:string;
    batchId: string;
    courseId: string;
    courseName: string;
    description: string;
    link: string;
    durationInHours: number;
    estimatedStartDate: Date;
    estimatedEndDate: Date;
    subjectMatterExpert: SME[];
    status:Status;
    feedback:string;
    actualStartDate:Date;
    actualEndDate:Date;
}

export enum Status {
    TO_BE_STARTED = 'TO_BE_STARTED',
    IN_PROGRESS = 'IN_PROGRESS',
    COMPLETED = 'COMPLETED'
}