"use client"

import { useState } from "react"
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
import { Input } from "@/components/ui/input"
import { useToast } from "@/components/hooks/use-toast"
import { SubmitButton } from "@/app/_components/SubmitButton"
import { verifyOtpAction } from "../_actions/verify-otp-action"
import { sendOtpAction } from "../_actions/send-otp-action"
import { OtpFormSchema } from "../_types/otp-form-schema"
import VerifyOtpRequest from "../_types/verify-otp-request"
import SendOtpRequest from "../_types/send-otp-request"

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

    const form = useForm<z.infer<typeof OtpFormSchema>>({
        resolver: zodResolver(OtpFormSchema),
        defaultValues: {
            otp: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof OtpFormSchema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: email,
            otp: formData.otp
        } as VerifyOtpRequest;
        const { success, error } = await verifyOtpAction(reqBody);
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
        } as SendOtpRequest;
        const { success, error } = await sendOtpAction(reqBody);
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
