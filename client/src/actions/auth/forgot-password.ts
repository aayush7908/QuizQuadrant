"use client"

import { setPasswordResetToken, setToken } from "@/lib/cookie-store";
import { serverEnv } from "@/lib/env/server";
import { req } from "@/lib/type/request/auth/forgot-password";
import { error } from "@/lib/type/response/error/error";

const forgotPasswordAPI = async (body: req) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/auth/forgot-password`, {
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
    forgotPasswordAPI
}