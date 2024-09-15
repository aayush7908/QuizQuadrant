import { z } from "zod";

const schema = z.object({
    name: z.string().min(3, { message: "Min. 3 characters" }).max(20, { message: "Max. 20 characters" })
});

export {
    schema
}