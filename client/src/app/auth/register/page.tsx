"use client"

import Link from "next/link"
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Checkbox } from "@/components/ui/checkbox"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { useContext, useState } from "react"
import { schema } from "@/lib/zod-schema/register/register"
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select"
import { registerAPI } from "@/actions/auth/register"
import { req } from "@/lib/type/request/auth/register"
import { AuthContext } from "@/context/auth/AuthContext"
import { useToast } from "@/components/ui/use-toast"
import { Loader, Loader2 } from "lucide-react"
import { useRouter } from "next/navigation"
import { SubmitButton } from "@/components/custom/SubmitButton"

export default function Register() {

    const [isPasswordVisible, setIsPasswordVisible] = useState<boolean>(false);
    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { authenticate } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            firstName: "",
            lastName: "",
            role: "",
            email: "",
            password: "",
            confirmPassword: ""
        }
    });

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const reqBody = {
            email: formData.email,
            password: formData.password,
            firstName: formData.firstName,
            lastName: formData.lastName,
            role: formData.role
        } as req;
        const { success, data, error } = await registerAPI(reqBody);
        if (success && data) {
            authenticate(data.user);
            toast({
                title: "Account created successfully"
            });
            router.push("/account/verify-email");
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
                <Card className="m-[1rem]">
                    <CardHeader>
                        <CardTitle className="text-xl">Sign Up</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="grid gap-4 md:gap-0 md:grid-cols-2">
                            <div className="flex flex-col gap-4 md:pe-2 md:border-r">
                                <div className="grid grid-cols-2 gap-4">
                                    <div className="grid gap-2">
                                        <FormField
                                            control={form.control}
                                            name="firstName"
                                            render={({ field }) => (
                                                <FormItem>
                                                    <FormLabel>First Name</FormLabel>
                                                    <FormControl>
                                                        <Input placeholder="John" {...field} type="text" required />
                                                    </FormControl>
                                                    <FormMessage />
                                                </FormItem>
                                            )}
                                        />
                                    </div>
                                    <div className="grid gap-2">
                                        <FormField
                                            control={form.control}
                                            name="lastName"
                                            render={({ field }) => (
                                                <FormItem>
                                                    <FormLabel>Last Name</FormLabel>
                                                    <FormControl>
                                                        <Input placeholder="Doe" {...field} type="text" required />
                                                    </FormControl>
                                                    <FormMessage />
                                                </FormItem>
                                            )}
                                        />
                                    </div>
                                </div>
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="role"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Role</FormLabel>
                                                <Select onValueChange={field.onChange} defaultValue={field.value}>
                                                    <FormControl>
                                                        <SelectTrigger>
                                                            <SelectValue placeholder="Select a Role" />
                                                        </SelectTrigger>
                                                    </FormControl>
                                                    <SelectContent>
                                                        <SelectGroup>
                                                            <SelectLabel>Select a Role</SelectLabel>
                                                            <SelectItem value="STUDENT">Student</SelectItem>
                                                            <SelectItem value="TEACHER">Teacher</SelectItem>
                                                        </SelectGroup>
                                                    </SelectContent>
                                                </Select>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="email"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Email</FormLabel>
                                                <FormControl>
                                                    <Input placeholder="me@gmail.com" {...field} type="email" required />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>
                            </div>
                            <div className="flex flex-col gap-4 md:ps-2">
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
                                <div className="mt-4 text-center text-sm">
                                    Already have an account?{" "}
                                    <Link href="/auth/login" className="underline">
                                        Login
                                    </Link>
                                </div>
                            </div>
                        </div>
                        <div className="flex justify-center mt-4">
                            <SubmitButton type="submit" displayName="Register" isProcessing={isProcessing} onSubmit={() => { }} />
                        </div>
                    </CardContent>
                </Card>
            </form>
        </Form>
    )
}
