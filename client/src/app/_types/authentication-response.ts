import { AuthenticatedUser } from "./authenticated-user";

export default interface AuthenticationResponse {
    token: string,
    user: AuthenticatedUserDto
};