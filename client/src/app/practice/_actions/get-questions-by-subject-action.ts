import { serverEnv } from "@/app/_lib/env-utils";
import ErrorResponse from "@/app/_types/error-response";
import Question from "@/app/_types/question";

const pageSize: number = 5;

const getQuestionsBySubjectAction = async (id: string, pageNumber: number) => {
    try {
        // API call
        const res = await fetch(`${serverEnv.BACKEND_BASE_URL}/question/get/by-subject/${id}?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
            method: "GET",
            cache: "no-cache",
            headers: {
                "Content-Type": "application/json"
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
    getQuestionsBySubjectAction
}