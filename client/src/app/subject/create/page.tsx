"use client"

import { z } from "zod";
import { SubjectForm } from "@/app/subject/_components/SubjectForm";
import { createSubjectAction } from "../_actions/create-subject-action";
import SubjectRequest from "../_types/subject-request";
import { SubjectFormSchema } from "../_types/subject-form-schema";

export default function CreateSubject() {

    const onSubmit = async (data: z.infer<typeof SubjectFormSchema>) => {
        const reqBody = {
            name: data.name
        } as SubjectRequest;
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