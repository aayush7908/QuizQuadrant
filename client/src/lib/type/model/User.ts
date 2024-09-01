type User = {
    id: string,
    email: string,
    firstName: string,
    lastName: string,
    profileImageUrl: string | undefined,
    accountCreatedOn: Date,
    role: string,
    isEmailVerified: boolean,
    totalQuestions: number,
    totalExams: number,
    totalDraftQuestions: number,
    totalDraftExams: number
};

export type {
    User
}