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
import { sendOtpAction } from "../_actions/send-otp-action"
import { EmailFormSchema } from "../_types/email-form-schema"
import SendOtpRequest from "../_types/send-otp-request"

export default function EmailForm({
    page,
    changePage,
    changeEmail
}: {
    page: number,
    changePage: Function,
    changeEmail: Function
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const form = useForm<z.infer<typeof EmailFormSchema>>({
        resolver: zodResolver(EmailFormSchema),
        defaultValues: {
            email: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof EmailFormSchema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: formData.email
        } as SendOtpRequest;
        const { success, error } = await sendOtpAction(reqBody);
        if (success) {
            toast({
                title: "OTP sent to email"
            });
            changeEmail(formData.email);
            changePage(page + 1);
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
                            name="email"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Email</FormLabel>
                                    <FormControl>
                                        <Input
                                            placeholder="me@gmail.com"
                                            {...field}
                                            type="email"
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
                        displayName="Send OTP"
                        isProcessing={isProcessing}
                        onSubmit={() => { }}
                    />
                </div>
            </form>
        </Form>
    )
}
