"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { schema } from "@/lib/zod-schema/forgot-password/email"
import { useContext, useState } from "react"
import { AuthContext } from "@/context/auth/AuthContext"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/auth/forgot-password"
import { forgotPasswordAPI } from "@/actions/auth/forgot-password"
import { Loader2 } from "lucide-react"
import { SubmitButton } from "../../SubmitButton"

export default function EmailForm({ page, changePage, changeEmail }: { page: number, changePage: Function, changeEmail: Function }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            email: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: formData.email
        } as req;
        const { success, error } = await forgotPasswordAPI(reqBody);
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
                                        <Input placeholder="me@gmail.com" {...field} type="email" required autoFocus={true} />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <SubmitButton type="submit" displayName="Send OTP" isProcessing={isProcessing} onSubmit={() => { }} />
                </div>
            </form>
        </Form>
    )
}
