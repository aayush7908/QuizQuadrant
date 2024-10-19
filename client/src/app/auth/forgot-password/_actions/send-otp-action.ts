import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import SendOtpRequest from "../_types/send-otp-request";

const sendOtpAction = async (body: SendOtpRequest) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/auth/reset-password/otp/send`, {
            method: "POST",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        // if successfull
        if (res.status === 200) {
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
    sendOtpAction
}