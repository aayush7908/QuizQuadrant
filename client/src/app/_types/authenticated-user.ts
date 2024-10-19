export interface AuthenticatedUser {
    id: string,
    email: string,
    profileImageUrl: string,
    role: string,
    isEmailVerified: boolean
};