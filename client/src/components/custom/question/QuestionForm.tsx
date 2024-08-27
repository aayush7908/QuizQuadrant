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
import { useFieldArray, useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { useContext, useEffect, useState } from "react"
import { schema } from "@/lib/zod-schema/question/question"
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Textarea } from "@/components/ui/textarea"
import { useRouter } from "next/navigation"
import { error } from "@/lib/type/response/error/error"
import { useToast } from "@/components/ui/use-toast"
import { SubmitButton } from "../SubmitButton"
import { SubjectContext } from "@/context/subject/SubjectContext"
import { Question } from "@/lib/type/model/question"

export default function QuestionForm({
    successMessage,
    onSubmit,
    defaultFormData
}: {
    successMessage: string,
    onSubmit: (formData: z.infer<typeof schema>) => Promise<{
        success: boolean;
        error?: error | undefined;
    }>,
    defaultFormData: Question | undefined
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { subjects } = useContext(SubjectContext);
    const { toast } = useToast();
    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: defaultFormData ? {
            visibility: defaultFormData.isPublic ? "PUBLIC" : "PRIVATE",
            type: defaultFormData.type,
            subject: defaultFormData.subtopic.subject.id,
            subtopic: defaultFormData.subtopic.id,
            statement: defaultFormData.statement,
            imageUrl: defaultFormData.imageUrl,
            options: [
                {
                    statement: defaultFormData.options[0].statement,
                    imageUrl: defaultFormData.options[0].imageUrl,
                    isCorrect: defaultFormData.options[0].isCorrect
                },
                {
                    statement: defaultFormData.options[1].statement,
                    imageUrl: defaultFormData.options[1].imageUrl,
                    isCorrect: defaultFormData.options[1].isCorrect
                },
                {
                    statement: defaultFormData.options[2].statement,
                    imageUrl: defaultFormData.options[2].imageUrl,
                    isCorrect: defaultFormData.options[2].isCorrect
                },
                {
                    statement: defaultFormData.options[3].statement,
                    imageUrl: defaultFormData.options[3].imageUrl,
                    isCorrect: defaultFormData.options[3].isCorrect
                }
            ],
            solution: {
                statement: defaultFormData.solution.statement,
                imageUrl: defaultFormData.solution.imageUrl
            }
        } : {
            visibility: "",
            type: "",
            subject: undefined,
            subtopic: "",
            statement: "",
            imageUrl: undefined,
            options: [
                {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                }, {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                }, {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                }, {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                }
            ],
            solution: {
                statement: "",
                imageUrl: undefined
            }
        }
    });

    const control = form.control;

    const { fields } = useFieldArray({
        control,
        name: "options"
    });

    const handleOnSubmit = async (data: z.infer<typeof schema>) => {
        setIsProcessing(true);
        const { success, error } = await onSubmit(data);
        if (success && data) {
            toast({
                title: successMessage
            });
            router.back();
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    useEffect(() => { }, [defaultFormData]);

    const onCancel = async () => {
        const isConfirm = window.confirm("All your data will be lost. Do you want to cancel ?");
        if (isConfirm) {
            router.back();
        }
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(handleOnSubmit)}>
                <div className="grid gap-4">
                    <div className="grid sm:grid-cols-2 gap-4">
                        <div className="grid gap-2">
                            <FormField
                                control={control}
                                name="visibility"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Question Visibility</FormLabel>
                                        <Select onValueChange={field.onChange} defaultValue={field.value}>
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Select question visibility" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Select question visibility</SelectLabel>
                                                    <SelectItem value="PUBLIC">Public</SelectItem>
                                                    <SelectItem value="PRIVATE">Private</SelectItem>
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
                                control={control}
                                name="type"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Question Type</FormLabel>
                                        <Select onValueChange={field.onChange} defaultValue={field.value}>
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Select question type" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Select question type</SelectLabel>
                                                    <SelectItem value="MCQ">MCQ</SelectItem>
                                                    <SelectItem value="MSQ">MSQ</SelectItem>
                                                </SelectGroup>
                                            </SelectContent>
                                        </Select>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                        </div>
                    </div>
                    <div className="grid sm:grid-cols-2 gap-4">
                        <div className="grid gap-2">
                            <FormField
                                control={control}
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
                        <div className="grid gap-2">
                            <FormField
                                control={control}
                                name="subtopic"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Subtopic</FormLabel>
                                        <Select onValueChange={field.onChange} defaultValue={field.value}>
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Select a subtopic" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Select a subtopic</SelectLabel>
                                                    {
                                                        subjects && form.getValues().subject && (
                                                            subjects.get(form.getValues().subject)?.subtopics.map((subtopic, index) => {
                                                                return (
                                                                    <SelectItem key={index} value={subtopic.id}>
                                                                        {subtopic.name}
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
                    </div>
                    <div className="grid gap-2">
                        <FormField
                            control={control}
                            name="statement"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Question</FormLabel>
                                    <FormControl>
                                        <Textarea placeholder="Enter question here ..." {...field} rows={5} required />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    {
                        fields.map((field, index) => {
                            return (
                                <div className="grid gap-2">
                                    <FormField
                                        control={control}
                                        name={`options.${index}.statement`}
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Option - {index + 1}</FormLabel>
                                                <FormControl>
                                                    <Textarea placeholder={`Enter option - ${index + 1} here ...`} {...field} onKeyUp={router.refresh} rows={5} required />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>
                            )
                        })
                    }
                    <div className="grid gap-2">
                        <p className="text-sm font-medium">Select correct option(s)</p>
                        {
                            form.getValues().type === "MCQ" && (
                                fields.map((field, index) => {
                                    return (
                                        <div key={index} onClick={() => { const newOptions = form.getValues().options; newOptions.forEach((option, i) => { option.isCorrect = (index === i) }); form.setValue("options", newOptions); }} className={`py-2 px-4 rounded-md cursor-pointer border ${field.isCorrect && "bg-green-400"}`}>
                                            {form.getValues("options")[index].statement}
                                        </div>
                                    )
                                })
                            )
                        }
                        {
                            form.getValues().type === "MSQ" && (
                                fields.map((field, index) => {
                                    return (
                                        <div key={index} onClick={() => { const newOptions = form.getValues().options; newOptions[index].isCorrect = !newOptions[index].isCorrect; form.setValue("options", newOptions); }} className={`py-2 px-4 rounded-md cursor-pointer border ${field.isCorrect && "bg-green-400"}`}>
                                            {form.getValues("options")[index].statement}
                                        </div>
                                    )
                                })
                            )
                        }
                        {
                            (form.getValues().type !== "MCQ" && form.getValues().type !== "MSQ") && (
                                <div className={`py-2 px-4 rounded-md border bg-red-300`}>
                                    Select question type first
                                </div>
                            )
                        }
                    </div>
                    <div className="grid gap-2">
                        <FormField
                            control={control}
                            name="solution.statement"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Solution</FormLabel>
                                    <FormControl>
                                        <Textarea placeholder="Enter solution here ..." {...field} rows={5} required />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="flex justify-center gap-3">
                        <SubmitButton displayName="Save" type="submit" isProcessing={isProcessing} onSubmit={() => { }} />
                        <SubmitButton variant="destructive" displayName="Cancel" type="button" isProcessing={isProcessing} onSubmit={onCancel} />
                    </div>
                </div>
            </form>
        </Form>
    )
}
