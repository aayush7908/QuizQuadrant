import { z } from "zod";
import { emailRegex, passwordRegex } from "../../regex";

const schema = z.object({
    email: z.string().regex(emailRegex, { message: "Enter a valid email" }),
    password: z.string().regex(passwordRegex, { message: "Enter a valid password" }),
});

export {
    schema
}