import { emailRegex, passwordRegex } from "@/app/_lib/regex-utils";
import { z } from "zod";


export const LoginFormSchema = z.object({
    email: z.string().regex(emailRegex, { message: "Enter a valid email" }),
    password: z.string().regex(passwordRegex, { message: "Enter a valid password" }),
});