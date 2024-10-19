export default interface ResetPasswordRequest {
    email: string,
    token: string,
    password: string
};