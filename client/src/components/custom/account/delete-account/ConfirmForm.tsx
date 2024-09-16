"use client"

import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { schema } from "@/lib/zod-schema/account/delete-account/confirm-form-schema"
import { useContext, useState } from "react"
import { useRouter } from "next/navigation"
import { deleteUserAction } from "@/actions/account/profile/delete-user-action"
import { useToast } from "@/components/ui/use-toast"
import { SubmitButton } from "../../SubmitButton"
import { removeToken } from "@/lib/cookie-store"
import { AuthContext } from "@/context/auth/AuthContext"

export default function ConfirmForm() {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { logout } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            delete: ""
        }
    });

    const onSubmit = async () => {
        setIsProcessing(true);
        const { success, error } = await deleteUserAction();
        if (success) {
            toast({
                title: "Account deleted successfully"
            });
            removeToken();
            logout();
            router.push("/");
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
                            name="delete"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Enter the word <i><b>delete</b></i> below to confirm</FormLabel>
                                    <FormControl>
                                        <Input {...field} type="text" required />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <SubmitButton variant="destructive" type="submit" displayName="Delete Account" isProcessing={isProcessing} onSubmit={() => { }} />
                </div>
            </form>
        </Form>
    )
}
