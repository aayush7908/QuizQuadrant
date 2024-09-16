"use client"

import { createSubtopicAction } from "@/actions/subtopic/create/create-subtopic-action";
import { SubtopicForm } from "@/components/custom/subtopic/SubtopicForm";
import { req } from "@/lib/type/request/subtopic/subtopic-form-request";
import { schema } from "@/lib/zod-schema/subtopic/subtopic-form-schema";
import { z } from "zod";

export default function CreateSubtopic() {

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name,
            subjectId: data.subject
        } as req;
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