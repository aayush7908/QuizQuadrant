"use client"

import { serverEnv } from "@/lib/env/server";
import { Subject } from "@/lib/type/model/Subject";
import { error } from "@/lib/type/response/error/error";

const getAllSubjectsAPI = async () => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/subject/get/all`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            }
        });

        // if successfull
        if (res.status === 200) {
            const data: Array<Subject> = await res.json();
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
    getAllSubjectsAPI
}