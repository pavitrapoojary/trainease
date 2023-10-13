import { CourseProgressStatus } from "./course-progress-status.model";

export interface BatchProgress {
    emailId: string; 
    courseProgressStatusList: CourseProgressStatus[]; 
}