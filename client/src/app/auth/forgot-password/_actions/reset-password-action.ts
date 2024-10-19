import { getPasswordResetToken, removePasswordResetToken } from "@/app/_lib/cookie-utils";
import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import ResetPasswordRequest from "../_types/reset-password-request";

const resetPasswordAction = async (body: ResetPasswordRequest) => {
    try {
        // extract password reset token
        const token: string | undefined = getPasswordResetToken();
        if (!token) {
            return {
                success: false
            };
        }
        body.token = token;

        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/user/reset-password`, {
            method: "POST",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        // if successfull
        if (res.status === 200) {
            removePasswordResetToken();
            return {
                success: true
            };
        }

        // if error
        const data: ErrorResponse = await res.json();
        return {
            success: false,
            error: data
        };

    } catch (err) {
        return {
            success: false,
            error: {
                errorMessage: "Some Error Occurred"
            } as ErrorResponse
        };
    }
}

export {
    resetPasswordAction
}