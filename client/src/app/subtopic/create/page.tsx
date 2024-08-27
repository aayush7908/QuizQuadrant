"use client"

import { createSubtopicAPI } from "@/actions/subtopic/create";
import { SubtopicForm } from "@/components/custom/subtopic/SubtopicForm";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { schema } from "@/lib/zod-schema/subtopic/subtopic";
import { z } from "zod";

export default function CreateSubtopic() {

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name,
            subject: {
                id: data.subject
            }
        } as Subtopic;
        return await createSubtopicAPI(reqBody);
    }

    return (
        <div className="h-full flex justify-center items-center">
            <SubtopicForm successMessage="Subtopic created successully" onSubmit={onSubmit} defaultFormData={undefined} />
        </div>
    );
}