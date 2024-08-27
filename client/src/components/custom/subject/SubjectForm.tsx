"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { SubmitButton } from "../SubmitButton";
import { useEffect, useState } from "react";
import { useToast } from "@/components/ui/use-toast";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { schema } from "@/lib/zod-schema/subject/subject";
import { zodResolver } from "@hookform/resolvers/zod";
import { Subject } from "@/lib/type/model/Subject";
import { useRouter } from "next/navigation";
import { error } from "@/lib/type/response/error/error";

export function SubjectForm({
    successMessage,
    onSubmit,
    defaultFormData
}: {
    successMessage: string,
    onSubmit: (formData: z.infer<typeof schema>) => Promise<{
        success: boolean;
        error?: error | undefined;
    }>,
    defaultFormData: Subject | undefined
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: defaultFormData ? {
            name: defaultFormData.name
        } : {
            name: ""
        }
    });

    const handleSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const { success, error } = await onSubmit(formData);
        if (success) {
            toast({
                title: successMessage
            });
            router.refresh();
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    useEffect(() => { }, [defaultFormData]);

    return (
        <Card className="m-[1rem]">
            <CardHeader>
                <CardTitle className="text-2xl">Subject</CardTitle>
            </CardHeader>
            <CardContent>
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(handleSubmit)}>
                        <div className="grid gap-4">
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="name"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Subject Name</FormLabel>
                                            <FormControl>
                                                <Input {...field} type="text" required autoFocus={true} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                            <SubmitButton type="submit" displayName="Save" isProcessing={isProcessing} onSubmit={() => { }} />
                        </div>
                    </form>
                </Form>
            </CardContent>
        </Card>
    );
}