"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { schema } from "@/lib/zod-schema/account/profile/name"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/user/update/name"
import { updateNameAPI } from "@/actions/user/update/name"
import { useState } from "react"
import { SubmitButton } from "../../SubmitButton"

export default function NameForm({ firstName, lastName, isNameEditable, toggleIsNameEditable }: { firstName: string, lastName: string, isNameEditable: boolean, toggleIsNameEditable: Function }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            firstName: firstName,
            lastName: lastName
        }
    });

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const reqBody = {
            firstName: formData.firstName,
            lastName: formData.lastName
        } as req;
        const { success, error } = await updateNameAPI(reqBody);
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
            <form onSubmit={form.handleSubmit(onSubmit)}>
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
