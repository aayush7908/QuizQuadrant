"use client"

import { useRouter } from "next/navigation"
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
import { Checkbox } from "@/components/ui/checkbox"
import { useToast } from "@/components/hooks/use-toast"
import { SubmitButton } from "@/app/_components/SubmitButton"
import { resetPasswordAction } from "../_actions/reset-password-action"
import { PasswordFormSchema } from "../_types/password-form-schema"
import ResetPasswordRequest from "../_types/reset-password-request"

export default function PasswordForm({
    email
}: {
    email: string
}) {

    const [isPasswordVisible, setIsPasswordVisible] = useState<boolean>(false);
    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof PasswordFormSchema>>({
        resolver: zodResolver(PasswordFormSchema),
        defaultValues: {
            password: "",
            confirmPassword: "",
        }
    });

    const onSubmit = async (formData: z.infer<typeof PasswordFormSchema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: email,
            password: formData.password,
            token: ""
        } as ResetPasswordRequest;
        const { success, error } = await resetPasswordAction(reqBody);
        if (success) {
            toast({
                title: "Password changed successfully"
            });
            router.push("/auth/login");
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
                            name="password"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Password</FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type={isPasswordVisible ? "text" : "password"}
                                            required
                                            autoFocus={true}
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="grid gap-2">
                        <FormField
                            control={form.control}
                            name="confirmPassword"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Confirm Password</FormLabel>
                                    <FormControl>
                                        <Input
                                            {...field}
                                            type={isPasswordVisible ? "text" : "password"}
                                            required
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="flex items-center gap-2 text-sm">
                        <Checkbox
                            id="showPassword"
                            onClick={() => {
                                setIsPasswordVisible(isPasswordVisible => !isPasswordVisible);
                            }}
                        />
                        <label htmlFor="showPassword">Show Password</label>
                    </div>
                    <SubmitButton
                        type="submit"
                        displayName="Save"
                        isProcessing={isProcessing}
                        onSubmit={() => { }}
                    />
                </div>
            </form>
        </Form>
    )
}
