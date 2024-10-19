import { z } from "zod";

export const SubtopicFormSchema = z.object({
    name: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" }),
    subject: z.string().length(36, { message: "Select a subject" })
});