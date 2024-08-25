import { questionTypeRegex, questionVisibilityRegex } from "@/lib/regex";
import { z } from "zod";

const schema = z.object({
    visibility: z.string().regex(questionVisibilityRegex, { message: "Select question visibility" }),
    type: z.string().regex(questionTypeRegex, { message: "Select question type" }),
    subject: z.string().min(3, { message: "Select a subject" }).max(30, { message: "Select a subject" }),
    subtopic: z.string().min(3, { message: "Select a subtopic" }).max(30, { message: "Select a subtopic" }),
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
            let flag: boolean = false;
            for (let i = 0; i < options.length && !flag; i++) {
                flag = options[i].isCorrect;
            }
            return flag;
        }),
    solution: z.object({
        statement: z.string().min(10, { message: "Min. 10 characters" }).max(65535, { message: "Max. 65535 characters" }),
        imageUrl: z.string().max(255, { message: "Max. 255 characters" }).optional()
    })
});

export {
    schema
}