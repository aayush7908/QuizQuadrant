"use client"

import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage
} from "@/components/ui/form"
import { schema } from "@/lib/zod-schema/auth/forgot-password/otp-form-schema"
import { useState } from "react"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/auth/forgot-password/otp-form-request"
import { req as sendOtpReq } from "@/lib/type/request/auth/forgot-password/email-form-request"
import { otpFormAction } from "@/actions/auth/forgot-password/otp-form-action"
import { emailFormAction } from "@/actions/auth/forgot-password/email-form-action"
import { SubmitButton } from "../../SubmitButton"

export default function OtpForm({
    page,
    changePage,
    email
}: {
    page: number,
    changePage: Function,
    email: string
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            otp: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: email,
            otp: formData.otp
        } as req;
        const { success, error } = await otpFormAction(reqBody);
        if (success) {
            toast({
                title: "Email verified successfully"
            });
            changePage(page + 1);
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    const handleResendOtp = async () => {
        setIsProcessing(true);
        const reqBody = {
            email: email
        } as sendOtpReq;
        const { success, error } = await emailFormAction(reqBody);
        if (success) {
            toast({
                title: "OTP sent to your email"
            });
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)}>
                <div className="grid gap-4">
                    <div className="grid gap-2">
                        <FormField
                            control={form.control}
                            name="otp"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Otp</FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type="text"
                                            required
                                            autoFocus={true}
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <SubmitButton
                        type="submit"
                        displayName="Verify"
                        isProcessing={isProcessing}
                        onSubmit={() => { }}
                    />
                    <SubmitButton
                        type="button"
                        displayName="Resend OTP"
                        isProcessing={isProcessing}
                        onSubmit={handleResendOtp}
                    />
                </div>
            </form>
        </Form>
    )
}
