"use client"

import { createSubjectAction } from "@/actions/subject/create/create-subject-action";
import { SubjectForm } from "@/components/custom/subject/SubjectForm";
import { req } from "@/lib/type/request/subject/subject-form-request";
import { schema } from "@/lib/zod-schema/subject/subject-form-schema";
import { z } from "zod";

export default function CreateSubject() {

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name
        } as req;
        return await createSubjectAction(reqBody);
    }

    return (
        <div className="h-full flex justify-center items-center">
            <SubjectForm
                successMessage="Subject Created Successully"
                onSubmit={onSubmit}
                defaultFormData={undefined}
            />
        </div>
    );
}