import { z } from "zod";

export const UserNameFormSchema = z.object({
    firstName: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" }),
    lastName: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" })
});