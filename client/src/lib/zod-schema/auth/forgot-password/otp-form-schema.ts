import { emailRegex } from "@/lib/regex";
import { z } from "zod";

const schema = z.object({
    otp: z.string().min(8, { message: "Enter a valid OTP" }).max(8, { message: "Enter a valid OTP" })
});

export {
    schema
}