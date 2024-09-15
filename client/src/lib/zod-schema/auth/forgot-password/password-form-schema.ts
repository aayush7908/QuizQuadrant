import { passwordRegex } from "@/lib/regex";
import { z } from "zod";

const schema = z.object({
    password: z.string().regex(passwordRegex, { message: "Enter a valid password" }),
    confirmPassword: z.string().regex(passwordRegex, { message: "Enter a valid password" })
}).refine(obj => {
    return obj.password === obj.confirmPassword
}, { message: "Passwords donot match" });

export {
    schema
}