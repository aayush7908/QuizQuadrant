import { questionTypeRegex, questionVisibilityRegex } from "@/lib/regex";
import { z } from "zod";

const schema = z.object({
    visibility: z.string().regex(questionVisibilityRegex, { message: "Select question visibility" }),
    type: z.string().regex(questionTypeRegex, { message: "Select question type" }),
    subject: z.string().length(36, { message: "Select a subject" }),
    subtopic: z.string().length(36, { message: "Select a subtopic" }),
    statement: z.string().min(10, { message: "Min. 10 characters" }).max(65535, { message: "Max. 65535 characters" }),
    imageUrl: z.string().max(255, { message: "Max. 255 characters" }).optional(),
    options: z.array(
        z.object({
            statement: z.string().min(10, { message: "Min. 10 characters" }).max(65535, { message: "Max. 65535 characters" }),
            imageUrl: z.string().max(255, { message: "Max. 255 characters" }).optional(),
            isCorrect: z.boolean()
        })
    )
        .length(4)
        .refine(options => {
            return (
                options[0].isCorrect ||
                options[1].isCorrect ||
                options[2].isCorrect ||
                options[3].isCorrect
            );
        }),
    solution: z.object({
        statement: z.string().min(10, { message: "Min. 10 characters" }).max(65535, { message: "Max. 65535 characters" }),
        imageUrl: z.string().max(255, { message: "Max. 255 characters" }).optional()
    })
});

export {
    schema
}