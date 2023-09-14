export interface User {
    emailId: string;
    name: string;
    role: UserRole;
    batchId: string;
}

export enum UserRole {
    ADMIN = 'ADMIN',
    TRAINEE = 'TRAINEE',
}
