"use client";

import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import Subject from "@/app/_types/subject";

const getSubjectAction = async (id: string) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/subject/get/by-id/${id}`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            }
        });

        // if successfull
        if (res.status === 200) {
            const data: Subject = await res.json();
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
    getSubjectAction
}