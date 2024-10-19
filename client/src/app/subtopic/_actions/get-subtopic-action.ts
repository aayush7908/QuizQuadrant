import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import Subtopic from "@/app/_types/subtopic";

const getSubtopicAction = async (id: string) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/subtopic/get/by-id/${id}`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
            }
        });

        // if successfull
        if (res.status === 200) {
            const data: Subtopic = await res.json();
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
    getSubtopicAction
}