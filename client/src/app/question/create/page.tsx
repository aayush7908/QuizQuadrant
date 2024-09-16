"use client"

import { createDraftQuestionAction } from "@/actions/question/create/create-draft-action";
import { createQuestionAction } from "@/actions/question/create/create-question-action";
import QuestionForm from "@/components/custom/question/QuestionForm";
import { req } from "@/lib/type/request/question/question-form-request";

export default function CreateQuestion() {

    const onSubmit = async (data: req) => {
        return await createQuestionAction(data);
    }

    const onSubmitDraft = async (data: req) => {
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