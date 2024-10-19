import { questionTypeRegex, questionVisibilityRegex } from "@/app/_lib/regex-utils";
import { z } from "zod";

export const QuestionFormSchema = z.object({
    type: z.string().regex(questionTypeRegex, { message: "Select question type" }),
    visibility: z.string().regex(questionVisibilityRegex, { message: "Select question visibility" }),
    subjectId: z.string().length(36, { message: "Select a subject" }),
    subtopicId: z.string().length(36, { message: "Select a subtopic" }),
    statement: z.string().min(10, { message: "Min. 10 characters" }).max(65535, { message: "Max. 65535 characters" }),
    imageUrl: z.string().max(255, { message: "Max. 255 characters" }).nullable().optional(),
    options: z.array(
        z.object({
            statement: z.string().min(10, { message: "Min. 10 characters" }).max(65535, { message: "Max. 65535 characters" }),
            imageUrl: z.string().max(255, { message: "Max. 255 characters" }).nullable().optional(),
            isCorrect: z.boolean()
        })
    ).length(4),
    solution: z.object({
        statement: z.string().min(10, { message: "Min. 10 characters" }).max(65535, { message: "Max. 65535 characters" }),
        imageUrl: z.string().max(255, { message: "Max. 255 characters" }).nullable().optional()
    })
}).refine(question => {
    return (
        question.options[0].isCorrect ||
        question.options[1].isCorrect ||
        question.options[2].isCorrect ||
        question.options[3].isCorrect
    );
}, { message: "Select correct option(s)", path: ["options.3.statement"] });