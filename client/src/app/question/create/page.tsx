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

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        const reqBody = {
            type: formData.type,
            isPublic: formData.visibility === "PUBLIC",
            statement: formData.statement,
            imageUrl: formData.imageUrl,
            subtopic: {
                id: formData.subtopic,
            } as Subtopic,
            options: [
                {
                    statement: formData.options[0].statement,
                    imageUrl: formData.options[0].imageUrl,
                    isCorrect: formData.options[0].isCorrect
                } as Option,
                {
                    statement: formData.options[1].statement,
                    imageUrl: formData.options[1].imageUrl,
                    isCorrect: formData.options[1].isCorrect
                } as Option,
                {
                    statement: formData.options[2].statement,
                    imageUrl: formData.options[2].imageUrl,
                    isCorrect: formData.options[2].isCorrect
                } as Option,
                {
                    statement: formData.options[3].statement,
                    imageUrl: formData.options[3].imageUrl,
                    isCorrect: formData.options[3].isCorrect
                } as Option
            ],
            solution: {
                statement: formData.solution.statement,
                imageUrl: formData.solution.imageUrl
            } as Solution
        } as Question;
        return await createQuestionAPI(reqBody);
    }

    return (
        <div className="py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
            <QuestionForm successMessage="Question created successfully" onSubmit={onSubmit} defaultFormData={undefined} />
        </div>
    );
}