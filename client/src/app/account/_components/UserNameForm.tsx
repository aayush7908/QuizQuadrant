"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { useState } from "react"
import UpdateUserNameRequest from "../_types/update-user-name-request"
import ErrorResponse from "@/app/_types/error-response"
import { useToast } from "@/components/hooks/use-toast"
import { UserNameFormSchema } from "../_types/user-name-form-schema"
import { SubmitButton } from "@/app/_components/SubmitButton"

export default function UserNameForm({
    firstName,
    lastName,
    isNameEditable,
    toggleIsNameEditable,
    onSubmit
}: {
    firstName: string,
    lastName: string,
    isNameEditable: boolean,
    toggleIsNameEditable: Function,
    onSubmit(body: UpdateUserNameRequest): Promise<{
        success: boolean;
        error?: undefined;
    } | {
        success: boolean;
        error: ErrorResponse;
    }>
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const form = useForm<z.infer<typeof UserNameFormSchema>>({
        resolver: zodResolver(UserNameFormSchema),
        defaultValues: {
            firstName: firstName,
            lastName: lastName
        }
    });

    const handleSubmit = async (formData: z.infer<typeof UserNameFormSchema>) => {
        setIsProcessing(true);
        const reqBody = {
            firstName: formData.firstName,
            lastName: formData.lastName
        } as UpdateUserNameRequest;
        const { success, error } = await onSubmit(reqBody);
        if (success) {
            toast({
                title: "Name changed successfully"
            });
            toggleIsNameEditable();
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
            <form onSubmit={form.handleSubmit(handleSubmit)}>
                <div className="grid gap-4">
                    <div className="grid gap-3">
                        <FormField
                            control={form.control}
                            name="firstName"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>First Name</FormLabel>
                                    <FormControl>
                                        <Input {...field} type="text" disabled={!isNameEditable} required />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="lastName"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Last Name</FormLabel>
                                    <FormControl>
                                        <Input {...field} type="text" disabled={!isNameEditable} required />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="flex justify-center gap-3">
                        {
                            isNameEditable ? (
                                <>
                                    <SubmitButton type="submit" displayName="Save" isProcessing={isProcessing} onSubmit={() => { }} />
                                    <Button variant="destructive" type="button" onClick={() => { toggleIsNameEditable(); form.setValue("firstName", firstName); form.setValue("lastName", lastName); }}>
                                        Cancel
                                    </Button>
                                </>
                            ) : (
                                <span className="bg-black text-white py-2 px-3 rounded-md cursor-pointer" onClick={() => toggleIsNameEditable()}>
                                    Edit
                                </span>
                            )
                        }
                    </div>
                </div>
            </form>
        </Form>
    )
}
