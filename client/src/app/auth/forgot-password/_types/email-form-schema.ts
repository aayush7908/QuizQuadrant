import { emailRegex } from "@/app/_lib/regex-utils";
import { z } from "zod";

export const EmailFormSchema = z.object({
    email: z.string().regex(emailRegex, { message: "Enter a valid email" })
});