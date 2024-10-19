import { getToken, removeToken, setToken } from "@/app/_lib/cookie-utils";
import { serverEnv } from "@/app/_lib/env-utils";
import AuthenticationResponse from "../_types/authentication-response";
import ErrorResponse from "../_types/error-response";

const authenticateAction = async () => {
    try {
        // extract token from cookies
        const token: string | undefined = getToken();
        if (!token) {
            return {
                success: true
            };
        }

        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/user/authenticate`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Authorization": `Bearer ${token}`
            }
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
        removeToken();
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
    authenticateAction
}