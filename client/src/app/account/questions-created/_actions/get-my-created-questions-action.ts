import { getToken } from "@/app/_lib/cookie-utils";
import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import Question from "@/app/_types/question";

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
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/question/get/my/created?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });

        // if successfull
        if (res.status === 200) {
            const data: Question[] = await res.json();
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
    getMyQuestionsAction
}