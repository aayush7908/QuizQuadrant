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
import { schema } from "@/lib/zod-schema/auth/forgot-password/otp-form-schema"
import { useContext, useEffect, useState } from "react"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/auth/forgot-password/otp-form-request"
import { req as sendOtpReq } from "@/lib/type/request/auth/forgot-password/email-form-request"
import { AuthContext } from "@/context/auth/AuthContext"
import { useRouter } from "next/navigation"
import { verifyEmailAction } from "@/actions/account/verify-email/verify-email-action"
import { SubmitButton } from "@/components/custom/SubmitButton"
import { emailFormAction } from "@/actions/auth/forgot-password/email-form-action"

export default function VerifyEmail() {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { user, verifyEmail } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            otp: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: user?.email,
            otp: formData.otp
        } as req;
        const { success, error } = await verifyEmailAction(reqBody);
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
        const reqBody = {
            email: user?.email
        } as sendOtpReq;
        console.log(reqBody);
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

    useEffect(() => {
        if (!user || user.isEmailVerified) {
            toast({
                title: "Email already verified",
                variant: "destructive"
            });
            router.push("/");
        }
    }, []);

    return (
        <>
            {
                user && !user.isEmailVerified ? (
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
                ) : (
                    null
                )
            }
        </>
    )
}
