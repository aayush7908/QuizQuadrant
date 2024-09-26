"use client"

import { getToken } from "@/lib/cookie-store";
import { serverEnv } from "@/lib/env/server";
import { req } from "@/lib/type/request/account/profile/user-name-form-request";
import { error } from "@/lib/type/response/error/error-response";

const updateUserNameAction = async (body: req, id: string) => {
    try {
        // extract token from cookies
        const token: string | undefined = getToken();
        if (!token) {
            return {
                success: true
            };
        }

        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/admin/user/update/name/${id}`, {
            method: "PATCH",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
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
    updateUserNameAction
}