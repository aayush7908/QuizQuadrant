"use client"

import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Question } from "@/lib/type/model/Question";
import Link from "next/link";
import { SubmitButton } from "../../SubmitButton";
import { useState } from "react";
import { useToast } from "@/components/ui/use-toast";
import { deleteQuestionAPI } from "@/actions/question/delete";

export function QuestionCard({ data, index, removeFunction }: { data: Question, index: number, removeFunction(index: number): void }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const handleDeleteQuestion = async () => {
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
        const { success, error } = await deleteQuestionAPI(data.id);
        if (success) {
            toast({
                title: "Question deleted successfully"
            });
            removeFunction(index);
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
        setIsProcessing(false);
    }

    return (
        <Card>
            <CardHeader className="border-b p-[1rem] bg-muted">
                <CardTitle className="flex justify-between items-center">
                    <span className="text-lg">Question - {index + 1}</span>
                    <Badge className="text-sm">{data.type}</Badge>
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="mt-[1rem] grid gap-3">
                    <div className="flex flex-wrap gap-3">
                        <Badge variant="outline" className="text-base">{data.subtopic.subject.name}</Badge>
                        <Badge variant="outline" className="text-base">{data.subtopic.name}</Badge>
                        <Badge variant="secondary" className="text-base border border-gray-400">{data.isPublic ? "PUBLIC" : "PRIVATE"}</Badge>
                    </div>
                    <span>{data.statement}</span>
                    <div className="w-full grid gap-2">
                        {
                            data.options.map((option, i) => {
                                return (
                                    <div key={i} className={`py-2 px-4 rounded-md border ${option.isCorrect && "bg-green-400"}`}>{option.statement}</div>
                                )
                            })
                        }
                    </div>
                    <div className="grid gap-3 p-3 border rounded-md bg-muted">
                        <h3 className="text-center font-semibold">SOLUTION</h3>
                        <span>{data.solution.statement}</span>
                    </div>
                </div>
            </CardContent>
            <CardFooter className="flex flex-col gap-3">
                <div className="w-full flex gap-3 justify-center">
                    <Link href={`/question/edit/${data.id}`} className="bg-black text-white py-2 px-3 rounded-md cursor-pointer">Edit</Link>
                    <SubmitButton type="button" displayName="Delete" variant="destructive" isProcessing={isProcessing} onSubmit={handleDeleteQuestion} />
                </div>
                <span className="w-full text-end">Last Modified On: {(new Date(data.lastModifiedOn)).toLocaleString()}</span>
            </CardFooter>
        </Card>
    );
}