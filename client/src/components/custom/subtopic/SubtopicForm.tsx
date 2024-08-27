"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useContext, useEffect, useState } from "react";
import { useToast } from "@/components/ui/use-toast";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { schema } from "@/lib/zod-schema/subtopic/subtopic";
import { zodResolver } from "@hookform/resolvers/zod";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select";
import { SubjectContext } from "@/context/subject/SubjectContext";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { error } from "@/lib/type/response/error/error";
import { SubmitButton } from "../SubmitButton";
import { RefreshContext } from "@/context/refresh/RefreshContext";

export function SubtopicForm({
    successMessage,
    onSubmit,
    defaultFormData
}: {
    successMessage: string,
    onSubmit: (formData: z.infer<typeof schema>) => Promise<{
        success: boolean;
        error?: error | undefined;
    }>,
    defaultFormData: Subtopic | undefined
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { subjects } = useContext(SubjectContext);
    const { refresh } = useContext(RefreshContext);
    const { toast } = useToast();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: defaultFormData ? {
            name: defaultFormData.name,
            subject: defaultFormData.subject.id
        } : {
            name: "",
            subject: ""
        }
    });

    const handleSubmit = async (formData: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const { success, error } = await onSubmit(formData);
        if (success) {
            toast({
                title: successMessage
            });
            refresh();
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
                <CardTitle className="text-2xl">Subtopic</CardTitle>
                <CardDescription>
                    Create Subtopic From Here
                </CardDescription>
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
                                            <FormLabel>Subtopic Name</FormLabel>
                                            <FormControl>
                                                <Input {...field} type="text" required autoFocus={true} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="subject"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Subject</FormLabel>
                                            <Select onValueChange={field.onChange} defaultValue={field.value}>
                                                <FormControl>
                                                    <SelectTrigger>
                                                        <SelectValue placeholder="Select a subject" />
                                                    </SelectTrigger>
                                                </FormControl>
                                                <SelectContent>
                                                    <SelectGroup>
                                                        <SelectLabel>Select a subject</SelectLabel>
                                                        {
                                                            subjects && (
                                                                Array.from(subjects.values()).map((subject, index) => {
                                                                    return (
                                                                        <SelectItem key={index} value={subject.id}>
                                                                            {subject.name}
                                                                        </SelectItem>
                                                                    )
                                                                })
                                                            )
                                                        }
                                                    </SelectGroup>
                                                </SelectContent>
                                            </Select>
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