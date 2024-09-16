"use client"

import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage
} from "@/components/ui/form";
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle
} from "@/components/ui/card";
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import { useContext, useEffect, useState } from "react";
import { useToast } from "@/components/ui/use-toast";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { schema } from "@/lib/zod-schema/subtopic/subtopic-form-schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { SubjectContext } from "@/context/subject/SubjectContext";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { error } from "@/lib/type/response/error/error-response";
import { SubmitButton } from "../SubmitButton";
import { RefreshContext } from "@/context/refresh/RefreshContext";
import { usePathname, useRouter } from "next/navigation";
import { deleteSubtopicAction } from "@/actions/subtopic/delete-subtopic-action";

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
    const router = useRouter();
    const { toast } = useToast();
    const path = usePathname();

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

    const handleDeleteSubtopic = async () => {
        if (!defaultFormData) {
            return;
        }
        setIsProcessing(true);
        const isConfirm = window.confirm("All data related to this subtopic will be deleted and cannot be retrieved back again. Are you sure to delete this Subtopic ?");
        if (!isConfirm) {
            return;
        }
        const str = window.prompt("Write delete below to confirm.");
        if (str !== "delete") {
            toast({
                title: "Action aborted",
                variant: "destructive"
            });
            return;
        }
        const { success, error } = await deleteSubtopicAction(defaultFormData.id);
        if (success) {
            toast({
                title: "Subtopic deleted successfully"
            });
            refresh();
            router.back();
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

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
                                                <Input
                                                    {...field}
                                                    type="text"
                                                    required
                                                    autoFocus={true}
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
                                    name="subject"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Subject</FormLabel>
                                            <Select
                                                onValueChange={field.onChange}
                                                defaultValue={field.value}
                                            >
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
                            <div className="grid gap-2">
                                <SubmitButton
                                    type="submit"
                                    displayName="Save"
                                    isProcessing={isProcessing}
                                    onSubmit={() => { }}
                                />
                                {
                                    path.startsWith("/subtopic/edit") && (
                                        <SubmitButton
                                            variant="destructive"
                                            type="button"
                                            displayName="Delete"
                                            isProcessing={isProcessing}
                                            onSubmit={handleDeleteSubtopic}
                                        />
                                    )
                                }
                            </div>
                        </div>
                    </form>
                </Form>
            </CardContent>
        </Card>
    );
}