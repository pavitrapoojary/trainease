import { Batch } from "./batch.model";

export interface User {
    emailId: string;
    name: string;
    role: UserRole;
    batch: Batch;
    editing:boolean
}

export enum UserRole {
    ADMIN = 'ADMIN',
    SME = 'SME',
    TRAINEE = 'TRAINEE'
}
