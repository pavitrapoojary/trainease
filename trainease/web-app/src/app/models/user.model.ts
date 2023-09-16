export interface User {
    emailId: string;
    name: string;
    role: UserRole;
    batchId: string;
}

export enum UserRole {
    ADMIN = 'ADMIN',
    SME = 'SME',
    TRAINEE = 'TRAINEE'
}
