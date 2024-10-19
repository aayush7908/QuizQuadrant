import { getToken } from "@/app/_lib/cookie-utils";
import QuestionRequest from "../_types/question-request";
import { serverEnv } from "@/app/_lib/env-utils";
import IdResponse from "@/app/_types/id-response";
import ErrorResponse from "@/app/_types/error-response";

const createDraftQuestionAction = async (body: QuestionRequest) => {
    try {
        // extract token from cookies
        const token: string | undefined = getToken();
        if (!token) {
            return {
                success: true
            };
        }

        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/draft/question/create`, {
            method: "POST",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(body)
        });

        // if successfull
        if (res.status === 200) {
            const data: IdResponse = await res.json();
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
    createDraftQuestionAction
}