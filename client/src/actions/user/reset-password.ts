"use client"

import { getPasswordResetToken } from "@/lib/cookie-store";
import { serverEnv } from "@/lib/env/server";
import { req } from "@/lib/type/request/user/reset-password";
import { error } from "@/lib/type/response/error/error";

const resetPasswordAPI = async (body: req) => {
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
    resetPasswordAPI
}