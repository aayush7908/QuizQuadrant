"use client"

import { getToken } from "@/lib/cookie-store";
import { serverEnv } from "@/lib/env/server";
import { Question } from "@/lib/type/model/Question";
import { error } from "@/lib/type/response/error/error-response";

const pageSize: number = 5;

const getMyQuestionsAction = async (pageNumber: number) => {
    try {
        // extract token from cookies
        const token: string | undefined = getToken();
        if (!token) {
            return {
                success: true
            };
        }

        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/question/my-created?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });

        // if successfull
        if (res.status === 200) {
            const data: Array<Question> = await res.json();
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
    getMyQuestionsAction
}