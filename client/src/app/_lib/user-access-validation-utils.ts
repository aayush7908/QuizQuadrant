import { AuthenticatedUser } from "../_types/authenticated-user";

const validateAdminAccess = (user: AuthenticatedUser | undefined) => {
    return (
        user && user.role === "ADMIN"
    );
}

const validateTeacherAccess = (user: AuthenticatedUser | undefined) => {
    return (
        user && (
            user.role === "ADMIN" || (
                user.role === "TEACHER" &&
                user.isEmailVerified
            )
        )
    );
}

const validateStudentAccess = (user: AuthenticatedUser | undefined) => {
    return (
        user && (
            user.role === "ADMIN" || (
                user.role === "STUDENT" &&
                user.isEmailVerified
            )
        )
    );
}

const validateUserAccess = (user: AuthenticatedUser | undefined) => {
    return (
        user && (
            user.role === "ADMIN" ||
            user.isEmailVerified
        )
    );
}

export {
    validateAdminAccess,
    validateTeacherAccess,
    validateStudentAccess,
    validateUserAccess
}