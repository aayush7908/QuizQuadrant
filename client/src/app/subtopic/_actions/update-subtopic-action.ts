import { getToken } from "@/app/_lib/cookie-utils";
import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import SubtopicRequest from "../_types/subtopic-request";

const updateSubtopicAction = async (body: SubtopicRequest, id: string) => {
    try {
        // extract token from cookies
        const token: string | undefined = getToken();
        if (!token) {
            return {
                success: true
            };
        }

        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/subtopic/update/${id}`, {
            method: "PUT",
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
    updateSubtopicAction
}