import { passwordRegex } from "@/app/_lib/regex-utils";
import { z } from "zod";

export const PasswordFormSchema = z.object({
    password: z.string().regex(passwordRegex, { message: "Enter a valid password" }),
    confirmPassword: z.string().regex(passwordRegex, { message: "Enter a valid password" })
}).refine(obj => {
    return obj.password === obj.confirmPassword
}, { message: "Passwords donot match" });