"use client"

import { createQuestionAPI } from "@/actions/question/create";
import QuestionForm from "@/components/custom/question/QuestionForm";
import { Option } from "@/lib/type/model/Option";
import { Question } from "@/lib/type/model/Question";
import { Solution } from "@/lib/type/model/Solution";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { schema } from "@/lib/zod-schema/question/question";
import { z } from "zod";

export default function CreateQuestion() {

    const onSubmit = async (data: Question) => {
        return await createQuestionAPI(data);
    }

    return (
        <div className="py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
            <QuestionForm
                successMessage="Question created successfully"
                onSubmit={onSubmit}
                defaultFormData={undefined}
            />
        </div>
    );
}