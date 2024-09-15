import { emailRegex } from "@/lib/regex";
import { z } from "zod";

const schema = z.object({
    email: z.string().regex(emailRegex, { message: "Enter a valid email" })
});

export {
    schema
}