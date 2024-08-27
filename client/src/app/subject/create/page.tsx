"use client"

import { createSubjectAPI } from "@/actions/subject/create";
import { SubjectForm } from "@/components/custom/subject/SubjectForm";
import { Subject } from "@/lib/type/model/Subject";
import { schema } from "@/lib/zod-schema/subject/subject";
import { z } from "zod";

export default function CreateSubject() {

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name
        } as Subject;
        return await createSubjectAPI(reqBody);
    }

    return (
        <div className="h-full flex justify-center items-center">
            <SubjectForm successMessage="Subject created successully" onSubmit={onSubmit} defaultFormData={undefined} />
        </div>
    );
}