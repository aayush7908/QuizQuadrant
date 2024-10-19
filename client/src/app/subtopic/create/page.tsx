"use client"

import { z } from "zod";
import { SubtopicForm } from "@/app/subtopic/_components/SubtopicForm";
import { createSubtopicAction } from "../_actions/create-subtopic-action";
import { SubtopicFormSchema } from "../_types/subtopic-form-schema";
import SubtopicRequest from "../_types/subtopic-request";

export default function CreateSubtopic() {

    const onSubmit = async (data: z.infer<typeof SubtopicFormSchema>) => {
        const reqBody = {
            name: data.name,
            subjectId: data.subject
        } as SubtopicRequest;
        return await createSubtopicAction(reqBody);
    }

    return (
        <div className="h-full flex justify-center items-center">
            <SubtopicForm
                successMessage="Subtopic Created Successully"
                onSubmit={onSubmit}
                defaultFormData={undefined}
            />
        </div>
    );
}