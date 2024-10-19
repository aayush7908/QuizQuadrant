"use client"

import QuestionForm from "@/app/question/_components/QuestionForm";
import QuestionRequest from "../_types/question-request";
import { createQuestionAction } from "../_actions/create-question-action";
import { createDraftQuestionAction } from "../_actions/create-draft-action";

export default function CreateQuestion() {

    const onSubmit = async (data: QuestionRequest) => {
        return await createQuestionAction(data);
    }

    const onSubmitDraft = async (data: QuestionRequest) => {
        return await createDraftQuestionAction(data);
    }

    return (
        <div className="py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
            <QuestionForm
                successMessage="Question created successfully"
                onSubmit={onSubmit}
                onSubmitDraft={onSubmitDraft}
                defaultFormData={undefined}
            />
        </div>
    );
}