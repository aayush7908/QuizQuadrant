import { z } from "zod";
import { emailRegex, passwordRegex, roleRegex } from "../../regex";

const schema = z.object({
    firstName: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" }),
    lastName: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" }),
    role: z.string().regex(roleRegex, { message: "Select a role" }),
    email: z.string().regex(emailRegex, { message: "Enter a valid email" }),
    password: z.string().regex(passwordRegex, { message: "Enter a valid password" }),
    confirmPassword: z.string().regex(passwordRegex, { message: "Enter a valid password" })
}).refine(obj => {
    return obj.password === obj.confirmPassword
}, { message: "Passwords donot match", path: ["confirmPassword"] });

export {
    schema
}