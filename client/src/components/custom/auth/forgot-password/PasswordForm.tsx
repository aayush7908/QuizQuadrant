"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Checkbox } from "@/components/ui/checkbox"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { useState } from "react"
import { schema } from "@/lib/zod-schema/forgot-password/password"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/user/reset-password"
import { resetPasswordAPI } from "@/actions/user/reset-password"
import { useRouter } from "next/navigation"
import { Loader2 } from "lucide-react"
import { SubmitButton } from "../../SubmitButton"

export default function PasswordForm({ page, changePage, email }: { page: number, changePage: Function, email: string }) {

    const [isPasswordVisible, setIsPasswordVisible] = useState<boolean>(false);
    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            password: "",
            confirmPassword: "",
        }
    });

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: email,
            password: formData.password,
            token: ""
        } as req;
        const { success, error } = await resetPasswordAPI(reqBody);
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
                                        <Input {...field} type={isPasswordVisible ? "text" : "password"} required autoFocus={true} />
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
                                        <Input {...field} type={isPasswordVisible ? "text" : "password"} required />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="flex items-center gap-2 text-sm">
                        <Checkbox id="showPassword" onClick={() => setIsPasswordVisible(isPasswordVisible => !isPasswordVisible)} />
                        <label htmlFor="showPassword">Show Password</label>
                    </div>
                    <SubmitButton isProcessing={isProcessing} onSubmit={() => { }} />
                </div>
            </form>
        </Form>
    )
}
