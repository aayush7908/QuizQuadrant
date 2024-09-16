import { User } from "../type/model/User"

const validateAdminAccess = (user: User | undefined) => {
    return (
        user && user.role === "ADMIN"
    );
}

const validateTeacherAccess = (user: User | undefined) => {
    return (
        user && (
            user.role === "ADMIN" || (
                user.role === "TEACHER" &&
                user.isEmailVerified
            )
        )
    );
}

const validateStudentAccess = (user: User | undefined) => {
    return (
        user && (
            user.role === "ADMIN" || (
                user.role === "STUDENT" &&
                user.isEmailVerified
            )
        )
    );
}

const validateUserAccess = (user: User | undefined) => {
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