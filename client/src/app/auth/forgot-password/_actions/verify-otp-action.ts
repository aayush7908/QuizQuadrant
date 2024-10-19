import { setPasswordResetToken } from "@/app/_lib/cookie-utils";
import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import VerifyOtpRequest from "../_types/verify-otp-request";
import VerifyOtpResponse from "../_types/verify-otp-response";

const verifyOtpAction = async (body: VerifyOtpRequest) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/auth/reset-password/otp/verify`, {
            method: "POST",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        // if successfull
        if (res.status === 200) {
            const data: VerifyOtpResponse = await res.json();
            setPasswordResetToken(data.token);
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
    verifyOtpAction
}