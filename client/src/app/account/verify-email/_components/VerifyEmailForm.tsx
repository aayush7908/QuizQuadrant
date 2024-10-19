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
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle
} from "@/components/ui/card"
import { useContext, useEffect, useState } from "react"
import { AuthContext } from "@/context/auth/AuthContext"
import { useRouter } from "next/navigation"
import { SubmitButton } from "@/app/_components/SubmitButton"
import { useToast } from "@/components/hooks/use-toast"
import { OtpFormSchema } from "@/app/auth/forgot-password/_types/otp-form-schema"
import { verifyOtpAction } from "../_actions/verify-otp-action"
import VerifyOtpRequest from "../_types/verify-otp-request"
import { sendOtpAction } from "../_actions/send-otp-action"

export default function VerifyEmailForm() {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { user, verifyEmail } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof OtpFormSchema>>({
        resolver: zodResolver(OtpFormSchema),
        defaultValues: {
            otp: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof OtpFormSchema>) => {
        setIsProcessing(true);
        const reqBody = {
            otp: formData.otp
        } as VerifyOtpRequest;
        const { success, error } = await verifyOtpAction(reqBody);
        if (success) {
            toast({
                title: "Email verified successfully"
            });
            verifyEmail();
            router.push("/account/profile");
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
        const { success, error } = await sendOtpAction();
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
        <div className="h-full w-full flex justify-center items-center">
            <Card className="max-w-sm m-[1rem]">
                <CardHeader>
                    <CardTitle className="text-2xl">Verify Email</CardTitle>
                    <CardDescription>
                        Enter OTP sent to your email address
                    </CardDescription>
                </CardHeader>
                <CardContent>
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
                </CardContent>
            </Card>
        </div>
    )
}