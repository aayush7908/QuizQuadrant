import { z } from "zod";

const schema = z.object({
    title: z.string().min(3, { message: "Min. 3 characters" }).max(50, { message: "Max. 50 characters" }),
    startDateTime: z.string().datetime(),
    duration: z.number({ message: "Enter a valid input" }).gte(10, { message: "Min. duration is 10" }).lte(999, { message: "Max. duration is 999" })
});

export {
    schema
}