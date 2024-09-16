"use client"

import { getToken } from "@/lib/cookie-store";
import { serverEnv } from "@/lib/env/server";
import { Question } from "@/lib/type/model/Question";
import { error } from "@/lib/type/response/error/error-response";

const getQuestionByIdAction = async (id: string) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/question/get/by-id/${id}`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            }
        });

        // if successfull
        if (res.status === 200) {
            const data: Question = await res.json();
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
    getQuestionByIdAction
}