"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { schema } from "@/lib/zod-schema/forgot-password/otp"
import { useState } from "react"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/auth/verify-email"
import { verifyEmailAPI } from "@/actions/auth/verify-email"
import { Loader2 } from "lucide-react"
import { SubmitButton } from "../../SubmitButton"

export default function OtpForm({ page, changePage, email }: { page: number, changePage: Function, email: string }) {

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
        const { success, error } = await verifyEmailAPI(reqBody);
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
    )
}
