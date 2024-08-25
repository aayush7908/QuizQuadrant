"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { schema } from "@/lib/zod-schema/forgot-password/otp"
import { useContext, useEffect, useState } from "react"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/auth/verify-email"
import { AuthContext } from "@/context/auth/AuthContext"
import { useRouter } from "next/navigation"
import { verifyEmailAPI } from "@/actions/user/verify-email"
import { SubmitButton } from "@/components/custom/SubmitButton"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"

export default function VerifyEmail() {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();
    const router = useRouter();
    const { user, verifyEmail } = useContext(AuthContext);

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
        const { success, error } = await verifyEmailAPI(reqBody);
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
                                                    <Input {...field} type="text" required autoFocus={true} />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>
                                <SubmitButton isProcessing={isProcessing} onSubmit={() => { }} />
                            </div>
                        </form>
                    </Form>
                </CardContent>
            </Card>
        </div>
    )
}
