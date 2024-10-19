"use client"

import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { useContext, useState } from "react"
import { useRouter } from "next/navigation"
import { AuthContext } from "@/context/auth/AuthContext"
import { useToast } from "@/components/hooks/use-toast"
import { ConfirmFormSchema } from "../_types/confirm-form-schema"
import { deleteUserAction } from "../_actions/delete-user-action"
import { removeToken } from "@/app/_lib/cookie-utils"
import { SubmitButton } from "@/app/_components/SubmitButton"

export default function ConfirmForm() {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { logout } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof ConfirmFormSchema>>({
        resolver: zodResolver(ConfirmFormSchema),
        defaultValues: {
            delete: ""
        }
    });

    const onSubmit = async () => {
        setIsProcessing(true);
        const isConfirm = window.confirm("All data related to your account will be deleted and cannot be retrieved back again. Are you sure to delete your Account ?");
        if (!isConfirm) {
            return;
        }
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
