"use client"

import { usePathname, useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
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
    CardHeader,
    CardTitle
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { RefreshContext } from "@/context/refresh/RefreshContext";
import { useToast } from "@/components/hooks/use-toast";
import { SubmitButton } from "@/app/_components/SubmitButton";
import { deleteSubjectAction } from "../../_actions/delete-subject-action";
import { SubjectFormSchema } from "../_types/subject-form-schema";
import ErrorResponse from "@/app/_types/error-response";
import Subject from "@/app/_types/subject";

export function SubjectForm({
    successMessage,
    onSubmit,
    defaultFormData
}: {
    successMessage: string,
    onSubmit: (formData: z.infer<typeof SubjectFormSchema>) => Promise<{
        success: boolean;
        error?: ErrorResponse | undefined;
    }>,
    defaultFormData: Subject | undefined
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { refresh } = useContext(RefreshContext);
    const router = useRouter();
    const { toast } = useToast();
    const path = usePathname();

    const form = useForm<z.infer<typeof SubjectFormSchema>>({
        resolver: zodResolver(SubjectFormSchema),
        defaultValues: defaultFormData ? {
            name: defaultFormData.name
        } : {
            name: ""
        }
    });

    const handleDeleteSubject = async () => {
        if (!defaultFormData) {
            return;
        }
        setIsProcessing(true);
        const isConfirm = window.confirm("All data related to this subject will be deleted and cannot be retrieved back again. Are you sure to delete this Subject ?");
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
        const { success, error } = await deleteSubjectAction(defaultFormData.id);
        if (success) {
            toast({
                title: "Subject deleted successfully"
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

    const handleSubmit = async (formData: z.infer<typeof SubjectFormSchema>) => {
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
                                <SubmitButton
                                    type="submit"
                                    displayName="Save"
                                    isProcessing={isProcessing}
                                    onSubmit={() => { }}
                                />
                                {
                                    path.startsWith("/subject/edit") && (
                                        <SubmitButton
                                            variant="destructive"
                                            type="button"
                                            displayName="Delete"
                                            isProcessing={isProcessing}
                                            onSubmit={handleDeleteSubject}
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