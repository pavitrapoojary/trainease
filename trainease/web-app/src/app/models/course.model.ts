import { SME } from "./sme.model";

export interface Course {
    courseId: string;
    batchId: string;
    courseName: string;
    description: string;
    link: string;
    durationInHours: number;
    estimatedStartDate: Date;
    estimatedEndDate: Date;
    subjectMatterExpert: SME[];
}