"use client"

import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { Question } from "@/lib/type/model/question";
import Link from "next/link";
import { SubmitButton } from "../../SubmitButton";
import { useState } from "react";
import { useToast } from "@/components/ui/use-toast";
import { useRouter } from "next/navigation";
import { deleteQuestionAPI } from "@/actions/question/delete";

export function QuestionCard({ question, index }: { question: Question, index: number }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();
    const router = useRouter();

    const handleDeleteQuestion = async () => {
        const isConfirm = window.confirm("All data related to this question will be deleted and cannot be retrieved back again. Are you sure to delete this Question ?");
        if (!isConfirm) {
            return;
        }
        setIsProcessing(true);
        const { success, error } = await deleteQuestionAPI(question.id);
        if (success) {
            toast({
                title: "Question deleted successfully"
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

    return (
        <Card>
            <CardHeader className="border-b p-[1rem] bg-muted">
                <CardTitle className="flex justify-between items-center">
                    <span className="text-lg">Question - {index + 1}</span>
                    <Badge className="text-sm">{question.type}</Badge>
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="mt-[1rem] grid gap-3">
                    <div className="flex flex-wrap gap-3">
                        <Badge variant="secondary" className="text-base border border-gray-400">{question.isPublic ? "PUBLIC" : "PRIVATE"}</Badge>
                        <Badge variant="outline" className="text-base">{question.subtopic.subject.name}</Badge>
                        <Badge variant="outline" className="text-base">{question.subtopic.name}</Badge>
                    </div>
                    <span>{question.statement}</span>
                    <div className="w-full grid gap-2">
                        {
                            question.options.map((option, i) => {
                                return (
                                    <div key={i} className={`py-2 px-4 rounded-md border ${option.isCorrect && "bg-green-400"}`}>{option.statement}</div>
                                )
                            })
                        }
                    </div>
                    <div className="grid gap-3 p-3 border rounded-md bg-muted">
                        <h3 className="text-center font-semibold">SOLUTION</h3>
                        <span>{question.solution.statement}</span>
                    </div>
                </div>
            </CardContent>
            <CardFooter>
                <div className="w-full flex gap-3 justify-center">
                    <Link href={`/question/edit/${question.id}`} className="bg-black text-white py-2 px-3 rounded-md cursor-pointer">Edit</Link>
                    <SubmitButton type="button" displayName="Delete" variant="destructive" isProcessing={isProcessing} onSubmit={handleDeleteQuestion} />
                </div>
            </CardFooter>
        </Card>
    );
}