"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { schema } from "@/lib/zod-schema/account/delete-account/confirm"

export default function ConfirmForm() {

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            delete: ""
        }
    });

    const onSubmit = async () => {
        alert("delete-account");
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
                    <Button type="submit" variant="destructive" className="w-full text-lg font-bold">
                        Delete Account
                    </Button>
                </div>
            </form>
        </Form>
    )
}
