import { setToken } from "@/lib/cookie-store";
import { serverEnv } from "@/lib/env/server";
import { req } from "@/lib/type/request/auth/login";
import { res } from "@/lib/type/response/auth/authenticate";
import { error } from "@/lib/type/response/error/error";

const loginAPI = async (body: req) => {
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
            const data: res = await res.json();
            setToken(data.token);
            return {
                success: true,
                data: data
            };
        }

        // if error
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
    loginAPI
}