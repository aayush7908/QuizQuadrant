import { z } from "zod";

export const SubjectFormSchema = z.object({
    name: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" })
});