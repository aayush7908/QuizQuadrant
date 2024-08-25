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
import { useState } from "react"
import { schema } from "@/lib/zod-schema/question/question"
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Textarea } from "@/components/ui/textarea"
import { useRouter } from "next/navigation"

export default function QuestionForm() {

    const router = useRouter();

    const form = useForm<z.infer<typeof schema>>({
        resolver: zodResolver(schema),
        defaultValues: {
            visibility: "",
            type: "",
            subject: "",
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

    const onSubmit = async (data: z.infer<typeof schema>) => {
        console.log(data);
        alert("register");
    }

    const onCancel = async () => {
        const isConfirm = window.confirm("All your data will be lost. Do you want to cancel ?");
        if (isConfirm) {
            router.back();
        }
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)}>
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
                                                    <SelectItem value="sub-1">Subject - 1</SelectItem>
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
                                                    <SelectItem value="sub-1">Subtopic - 1</SelectItem>
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
                            fields.map((field, index) => {
                                return (
                                    <div onClick={() => { field.isCorrect = !field.isCorrect; router.refresh(); }} className={`py-2 px-4 rounded-md cursor-pointer border ${field.isCorrect && "bg-muted"}`}>
                                        {form.getValues("options")[index].statement}
                                    </div>
                                )
                            })
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
                        <Button type="submit">
                            Save
                        </Button>
                        <Button type="button" variant="destructive" onClick={onCancel}>
                            Cancel
                        </Button>
                    </div>
                </div>
            </form>
        </Form>
    )
}
