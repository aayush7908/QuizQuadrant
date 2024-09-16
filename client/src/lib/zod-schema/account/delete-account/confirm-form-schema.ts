import { deleteRegex } from "@/lib/regex";
import { z } from "zod";

const schema = z.object({
    delete: z.string().regex(deleteRegex, { message: "Enter a valid input" })
});

export {
    schema
}