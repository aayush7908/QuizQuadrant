import { setToken } from "@/app/_lib/cookie-utils";
import { serverEnv } from "@/app/_lib/env-utils";
import LoginRequest from "../_types/login-request";
import AuthenticationResponse from "@/app/_types/authentication-response";
import ErrorResponse from "@/app/_types/error-response";

const loginAction = async (body: LoginRequest) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/auth/login`, {
            method: "POST",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        // if successfull
        if (res.status === 200) {
            const data: AuthenticationResponse = await res.json();
            setToken(data.token);
            return {
                success: true,
                data: data
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
    loginAction
}