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

export default function PasswordForm({ page, changePage }: { page: number, changePage: Function }) {

    const [isPasswordVisible, setIsPasswordVisible] = useState<boolean>(false);

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            password: "",
            confirmPassword: "",
        }
    });

    const onSubmit = async (data: z.infer<typeof schema>) => {
        console.log(data);
        alert("forgot-password => reset-password");
        changePage(page + 1);
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
                                        <Input {...field} type={isPasswordVisible ? "text" : "password"} required />
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
                    <Button type="submit" className="w-full">
                        Reset Password
                    </Button>
                </div>
            </form>
        </Form>
    )
}
