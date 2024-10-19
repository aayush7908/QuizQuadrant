export default interface UserProfile {
    id: string,
    email: string,
    firstName: string,
    lastName: string,
    profileImageUrl: string | undefined,
    accountCreatedOn: Date,
    role: string,
    isEmailVerified: boolean,
    totalQuestions: number,
    totalUsers: number
};