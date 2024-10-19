"use client"

import { usePathname, useRouter } from "next/navigation"
import { useContext, useEffect, useState } from "react"
import { useFieldArray, useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage
} from "@/components/ui/form"
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue
} from "@/components/ui/select"
import { Textarea } from "@/components/ui/textarea"
import { useToast } from "@/components/hooks/use-toast"
import { SubjectContext } from "@/context/subject/SubjectContext"
import { SubmitButton } from "@/app/_components/SubmitButton"
import QuestionRequest from "../_types/question-request"
import ErrorResponse from "@/app/_types/error-response"
import IdResponse from "@/app/_types/id-response"
import Question from "@/app/_types/question"
import { QuestionFormSchema } from "../_types/question-form-schema"
import Subtopic from "@/app/_types/subtopic"

export default function QuestionForm({
    successMessage,
    onSubmit,
    onSubmitDraft,
    onDelete,
    defaultFormData
}: {
    successMessage: string,
    onSubmit: (formData: QuestionRequest) => Promise<{
        success: boolean;
        error?: ErrorResponse | undefined;
    }>,
    onSubmitDraft?: (formData: QuestionRequest) => Promise<{
        success: boolean;
        error?: ErrorResponse | undefined;
        data?: IdResponse;
    }>,
    onDelete?: () => Promise<{
        success: boolean;
        error?: ErrorResponse | undefined;
    }>,
    defaultFormData: Question | undefined
}) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { subjects } = useContext(SubjectContext);
    const { toast } = useToast();
    const router = useRouter();
    const path = usePathname();

    const createQuestionFromSchema = () => {
        const formData = form.getValues();
        return {
            type: formData.type,
            isPublic: formData.visibility === "PUBLIC",
            statement: formData.statement,
            imageUrl: formData.imageUrl,
            subtopic: {
                id: formData.subtopicId,
                subject: {
                    id: formData.subjectId
                }
            },
            options: [
                {
                    statement: formData.options[0].statement,
                    imageUrl: formData.options[0].imageUrl,
                    isCorrect: formData.options[0].isCorrect
                },
                {
                    statement: formData.options[1].statement,
                    imageUrl: formData.options[1].imageUrl,
                    isCorrect: formData.options[1].isCorrect
                },
                {
                    statement: formData.options[2].statement,
                    imageUrl: formData.options[2].imageUrl,
                    isCorrect: formData.options[2].isCorrect
                },
                {
                    statement: formData.options[3].statement,
                    imageUrl: formData.options[3].imageUrl,
                    isCorrect: formData.options[3].isCorrect
                }
            ],
            solution: {
                statement: formData.solution.statement,
                imageUrl: formData.solution.imageUrl
            }
        } as QuestionRequest;
    }

    const form = useForm<z.infer<typeof QuestionFormSchema>>({
        resolver: zodResolver(QuestionFormSchema),
        defaultValues: defaultFormData ? {
            type: defaultFormData.type,
            visibility: defaultFormData.isPublic ? "PUBLIC" : "PRIVATE",
            subjectId: defaultFormData.subtopic?.subject?.id || undefined,
            subtopicId: defaultFormData.subtopic?.id || undefined,
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
            type: "",
            visibility: "",
            subjectId: undefined,
            subtopicId: undefined,
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

    const handleOnSubmit = async () => {
        setIsProcessing(true);
        const { success, error } = await onSubmit(createQuestionFromSchema());
        if (success) {
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

    const handleSaveDraft = async () => {
        if (onSubmitDraft) {
            setIsProcessing(true);
            const { success, data, error } = await onSubmitDraft(createQuestionFromSchema());
            if (success && data) {
                toast({
                    title: "Draft saved successfully"
                });
                if (path.startsWith("/question/create")) {
                    router.push(`/question/draft/${data.id}`);
                }
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
            setIsProcessing(false);
        }
    }

    const handleDelete = async () => {
        if (onDelete) {
            const isConfirm = window.confirm("All data related to this question will be deleted and cannot be retrieved back again. Are you sure to delete this Question ?");
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
            setIsProcessing(true);
            const { success, error } = await onDelete();
            if (success) {
                toast({
                    title: "Data Deleted Successfully"
                });
                router.push("/account/questions-created");
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
            setIsProcessing(false);
        }
    }

    const onCancel = async () => {
        const isConfirm = window.confirm("All your data will be lost. Do you want to cancel ?");
        if (isConfirm) {
            router.back();
        }
    }

    useEffect(() => { }, [defaultFormData]);

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
                                        <Select
                                            onValueChange={field.onChange}
                                            defaultValue={field.value}
                                        >
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Select question visibility" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Select question visibility</SelectLabel>
                                                    <SelectItem value="PUBLIC">
                                                        Public
                                                    </SelectItem>
                                                    <SelectItem value="PRIVATE">
                                                        Private
                                                    </SelectItem>
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
                                        <Select
                                            onValueChange={field.onChange}
                                            defaultValue={field.value}
                                        >
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Select question type" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Select question type</SelectLabel>
                                                    <SelectItem value="MCQ">
                                                        MCQ
                                                    </SelectItem>
                                                    <SelectItem value="MSQ">
                                                        MSQ
                                                    </SelectItem>
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
                                name="subjectId"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Subject</FormLabel>
                                        <Select
                                            onValueChange={field.onChange}
                                            defaultValue={field.value}
                                            onOpenChange={router.refresh}
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
                                                                    <SelectItem
                                                                        key={index}
                                                                        value={subject.id}
                                                                    >
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
                                name="subtopicId"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Subtopic</FormLabel>
                                        <Select
                                            onValueChange={field.onChange}
                                            defaultValue={field.value}
                                        >
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Select a subtopic" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Select a subtopic</SelectLabel>
                                                    {
                                                        subjects && form.getValues().subjectId && (
                                                            subjects.get(form.getValues().subjectId)?.subtopics.map((subtopic: Subtopic, index: number) => {
                                                                return (
                                                                    <SelectItem
                                                                        key={index}
                                                                        value={subtopic.id}
                                                                    >
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
                                        <Textarea
                                            placeholder="Enter question here ..."
                                            {...field}
                                            rows={5}
                                            required
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    {
                        fields.map((field, index) => {
                            return (
                                <div key={index} className="grid gap-2">
                                    <FormField
                                        control={control}
                                        name={`options.${index}.statement`}
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Option - {index + 1}</FormLabel>
                                                <FormControl>
                                                    <Textarea
                                                        placeholder={`Enter option - ${index + 1} here ...`}
                                                        {...field}
                                                        onKeyUp={router.refresh}
                                                        rows={5}
                                                        required
                                                    />
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
                                        <div
                                            key={index}
                                            onClick={() => {
                                                const newOptions = form.getValues().options;
                                                newOptions.forEach((option, i) => {
                                                    option.isCorrect = (index === i)
                                                });
                                                form.setValue("options", newOptions);
                                            }}
                                            className={`py-2 px-4 rounded-md cursor-pointer border ${field.isCorrect && "bg-green-400"}`}
                                        >
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
                                        <div
                                            key={index}
                                            onClick={() => {
                                                const newOptions = form.getValues().options;
                                                newOptions[index].isCorrect = !newOptions[index].isCorrect;
                                                form.setValue("options", newOptions);
                                            }}
                                            className={`py-2 px-4 rounded-md cursor-pointer border ${field.isCorrect && "bg-green-400"}`}
                                        >
                                            {form.getValues("options")[index].statement}
                                        </div>
                                    )
                                })
                            )
                        }
                        {
                            (
                                form.getValues().type !== "MCQ" &&
                                form.getValues().type !== "MSQ"
                            ) && (
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
                                        <Textarea
                                            placeholder="Enter solution here ..."
                                            {...field}
                                            rows={5}
                                            required
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="flex flex-wrap justify-center gap-3">
                        {
                            !path.startsWith("/question/edit") && (
                                <SubmitButton
                                    displayName="Draft"
                                    type="button"
                                    isProcessing={isProcessing}
                                    onSubmit={handleSaveDraft}
                                />
                            )
                        }
                        <SubmitButton
                            displayName="Save"
                            type="submit"
                            isProcessing={isProcessing}
                            onSubmit={() => { }}
                        />
                        <SubmitButton
                            displayName="Cancel"
                            type="button"
                            isProcessing={isProcessing}
                            onSubmit={onCancel}
                        />
                        {
                            !path.startsWith("/question/create") &&
                            onDelete && (
                                <SubmitButton
                                    variant="destructive"
                                    displayName="Delete"
                                    type="button"
                                    isProcessing={isProcessing}
                                    onSubmit={handleDelete}
                                />
                            )
                        }
                    </div>
                </div>
            </form>
        </Form>
    )
}
