import { getToken, removeToken, setToken } from "@/lib/cookie-store";
import { serverEnv } from "@/lib/env/server";
import { res } from "@/lib/type/response/auth/authenticate";
import { error } from "@/lib/type/response/error/error-response";

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
            const data: res = await res.json();
            setToken(data.token);
            return {
                success: true,
                data: data
            };
        }

        // if error
        removeToken();
        const data: error = await res.json();
        return {
            success: false,
            error: data
        };

    } catch (err) {
        return {
            success: false,
            error: {
                errorMessage: "Some Error Occurred"
            } as error
        };
    }
}

export {
    authenticateAction
}