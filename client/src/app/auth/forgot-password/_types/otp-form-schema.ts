import { z } from "zod";

export const OtpFormSchema = z.object({
    otp: z.string().min(8, { message: "Enter a valid OTP" }).max(8, { message: "Enter a valid OTP" })
});