import { Batch } from "./batch.model";

export interface Course {
    courseId: string;
    batch: Batch;
    courseName: string;
    description: string;
    link: string;
    durationInHours: number;
    estimatedStartDate: Date;
    estimatedEndDate: Date;
    subjectMatterExpert: string;
    editing:boolean;
}