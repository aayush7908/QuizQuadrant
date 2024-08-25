import { z } from "zod";

const schema = z.object({
    firstName: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" }),
    lastName: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" })
});

export {
    schema
}