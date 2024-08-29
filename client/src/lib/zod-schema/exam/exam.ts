import { z } from "zod";
import { emailRegex } from "@/lib/regex";

const schema = z.object({
    title: z.string().min(3, { message: "Min. 3 characters" }).max(50, { message: "Max. 50 characters" }),
    startDateTime: z.string({ message: "Select a valid date-time" }),
    duration: z.number({ message: "Enter a valid input" }).gte(10, { message: "Min. duration is 10" }).lte(999, { message: "Max. duration is 999" }),
    questions: z.array(
        z.object({
            id: z.string().length(36, { message: "Select a subject" }),
        })
    ),
    candidates: z.array(
        z.object({
            email: z.string().regex(emailRegex, { message: "Enter a valid email" }),
        })
    )
});

export {
    schema
}