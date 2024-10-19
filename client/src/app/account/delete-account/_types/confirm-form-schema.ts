import { deleteRegex } from "@/app/_lib/regex-utils";
import { z } from "zod";

export const ConfirmFormSchema = z.object({
    delete: z.string().regex(deleteRegex, { message: "Enter a valid input" })
});