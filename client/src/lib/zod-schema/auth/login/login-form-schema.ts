import { emailRegex, passwordRegex } from "@/lib/regex";
import { z } from "zod";

const schema = z.object({
    email: z.string().regex(emailRegex, { message: "Enter a valid email" }),
    password: z.string().regex(passwordRegex, { message: "Enter a valid password" }),
});

export {
    schema
}