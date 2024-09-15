"use client"

import Link from "next/link"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Checkbox } from "@/components/ui/checkbox"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { useContext, useState } from "react"
import { schema } from "@/lib/zod-schema/auth/login/login-form-schema"
import { AuthContext } from "@/context/auth/AuthContext"
import { useToast } from "@/components/ui/use-toast"
import { req } from "@/lib/type/request/auth/login/login-form-request"
import { loginAction } from "@/actions/auth/login/login-form-action"
import { useRouter } from "next/navigation"
import { SubmitButton } from "@/components/custom/SubmitButton"

export default function Login() {

    const [isPasswordVisible, setIsPasswordVisible] = useState<boolean>(false);
    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { authenticate } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            email: "",
            password: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: formData.email,
            password: formData.password
        } as req;
        const { success, data, error } = await loginAction(reqBody);
        if (success && data) {
            authenticate(data.user);
            toast({
                title: "Logged in successfully"
            });
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
                <Card className="max-w-sm m-[1rem]">
                    <CardHeader>
                        <CardTitle className="text-2xl">Login</CardTitle>
                        <CardDescription>
                            Enter your email below to login to your account
                        </CardDescription>
                    </CardHeader>
                    <CardContent>
                        <div className="grid gap-4">
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="email"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Email</FormLabel>
                                            <FormControl>
                                                <Input
                                                    placeholder="me@gmail.com"
                                                    {...field}
                                                    type="email"
                                                    required
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
                                    name="password"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Password</FormLabel>
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
                            <div className="grid gap-2">
                                <div className="flex items-center gap-2 text-sm">
                                    <Checkbox
                                        id="showPassword"
                                        onClick={() => {
                                            setIsPasswordVisible(isPasswordVisible => !isPasswordVisible)
                                        }}
                                    />
                                    <label htmlFor="showPassword">Show Password</label>
                                </div>
                                <Link
                                    href="/auth/forgot-password"
                                    className="ml-auto inline-block text-sm underline"
                                >
                                    Forgot your password?
                                </Link>
                            </div>
                            <SubmitButton
                                type="submit"
                                displayName="Login"
                                isProcessing={isProcessing}
                                onSubmit={() => { }}
                            />
                        </div>
                        <div className="mt-4 text-center text-sm">
                            Don&apos;t have an account?{" "}
                            <Link href="/auth/register" className="underline">
                                Register
                            </Link>
                        </div>
                    </CardContent>
                </Card>
            </form>
        </Form>
    )
}
