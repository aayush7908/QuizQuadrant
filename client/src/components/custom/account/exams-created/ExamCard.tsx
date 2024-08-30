"use client"

import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Question } from "@/lib/type/model/Question";
import Link from "next/link";
import { SubmitButton } from "../../SubmitButton";
import { useState } from "react";
import { useToast } from "@/components/ui/use-toast";
import { deleteQuestionAPI } from "@/actions/question/delete";
import { Exam } from "@/lib/type/model/Exam";
import { deleteExamAPI } from "@/actions/exam/delete";

export function ExamCard({ data, index, removeFunction }: { data: Exam, index: number, removeFunction(index: number): void }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const { toast } = useToast();

    const handleDeleteExam = async () => {
        const isConfirm = window.confirm("All data related to this exam will be deleted and cannot be retrieved back again. Are you sure to delete this Exam ?");
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
        const { success, error } = await deleteExamAPI(data.id);
        if (success) {
            toast({
                title: "Exam deleted successfully"
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
                <CardTitle>
                    <span className="text-lg">{data.title}</span>
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="mt-[1rem] grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                    <div className="flex flex-col gap-1">
                        <span className="font-medium">Start Date Time</span>
                        <span className="py-2 px-3 bg-muted border rounded">{(new Date(data.startDateTime)).toLocaleString()}</span>
                    </div>
                    <div className="flex flex-col gap-1">
                        <span className="font-medium">Duration (in minutes)</span>
                        <span className="py-2 px-3 bg-muted border rounded">{data.durationInMinutes}</span>
                    </div>
                    <div className="flex flex-col gap-1">
                        <span className="font-medium">Total Marks</span>
                        <span className="py-2 px-3 bg-muted border rounded">{data.totalMarks}</span>
                    </div>
                    <div className="flex flex-col gap-1">
                        <span className="font-medium">No. Of Questions</span>
                        <span className="py-2 px-3 bg-muted border rounded">{data.questions.length}</span>
                    </div>
                    <div className="flex flex-col gap-1">
                        <span className="font-medium">No. Of Candidates</span>
                        <span className="py-2 px-3 bg-muted border rounded">{data.candidates.length}</span>
                    </div>
                </div>
            </CardContent>
            <CardFooter className="flex flex-col gap-3">
                <div className="w-full flex gap-3 justify-center">
                    <Link href={`/exam/edit/${data.id}`} className="bg-black text-white py-2 px-3 rounded-md cursor-pointer">Edit</Link>
                    <SubmitButton type="button" displayName="Delete" variant="destructive" isProcessing={isProcessing} onSubmit={handleDeleteExam} />
                </div>
                <span className="w-full text-end">Last Modified On: {(new Date(data.lastModifiedOn)).toLocaleString()}</span>
            </CardFooter>
        </Card>
    );
}