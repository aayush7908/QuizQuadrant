"use client"

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/components/hooks/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { Bookmark, BookmarkX, Loader2 } from "lucide-react";
import Link from "next/link";
import { useContext, useEffect, useState } from "react";
import Question from "@/app/_types/question";
import { Badge } from "@/components/ui/badge";
import { saveQuestionAction } from "@/app/_actions/save-question-action";

export function QuestionCard({ data, index }: { data: Question, index: number }) {

    const [choice, setChoice] = useState<Map<string, boolean>>(new Map());
    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [isAnswerCorrect, setIsAnswerCorrect] = useState<boolean>(false);
    const [isSolutionVisible, setIsSolutionVisible] = useState<boolean>(false);
    const { user } = useContext(AuthContext);
    const { toast } = useToast();

    const handleCheckAnswer = () => {
        let flag: boolean = true;
        data.options.forEach((option) => {
            flag = flag && (choice.get(option.id) === option.isCorrect);
        });
        setIsAnswerCorrect(flag);
        setIsSolutionVisible(true);
    }

    const handleSaveQuestion = async () => {
        if (!user) {
            toast({
                title: "Please Login to continue ...",
                variant: "destructive",
                action: <Link href={"/auth/login"} className="text-sm border rounded-sm px-3 py-2 hover:underline">Login</Link>
            });
        } else {
            setIsProcessing(true);
            const { success, error } = await saveQuestionAction(data.id);
            if (success) {
                toast({
                    title: "Question saved successfully"
                });
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
            setIsProcessing(false);
        }
    }

    const handleOptionSelection = (id: string) => {
        if (isSolutionVisible) {
            return;
        }
        const newChoice = new Map(choice);
        if (data.type === "MSQ") {
            newChoice.set(id, !choice.get(id));
        } else {
            data.options.forEach((option) => {
                newChoice.set(option.id, option.id === id);
            });
        }
        setChoice(newChoice);
    }

    useEffect(() => {
        const newChoice = new Map<string, boolean>();
        data.options.forEach((option) => {
            newChoice.set(option.id, false);
        });
        setChoice(newChoice);
    }, []);

    return (
        <Card>
            <CardHeader className="border-b p-[1rem] bg-muted rounded-t-md">
                <CardTitle className="flex justify-between items-center">
                    <span className="text-lg">Question - {index + 1}</span>
                    <Badge className="text-sm">{data.type}</Badge>
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="mt-[1rem] grid gap-3">
                    <div className="flex flex-wrap gap-3">
                        <Badge variant="secondary" className="text-base border-gray-400">{data.subtopic.subject.name}</Badge>
                        <Badge variant="secondary" className="text-base border-gray-400">{data.subtopic.name}</Badge>
                    </div>
                    <span>{data.statement}</span>
                    <div className="w-full grid gap-2">
                        {
                            data.options.map((option, index) => {
                                return (
                                    <div
                                        key={index}
                                        className={`py-2 px-4 rounded-md cursor-pointer border ${choice.get(option.id) && "bg-muted border-gray-500"}`}
                                        onClick={() => { handleOptionSelection(option.id) }}
                                    >
                                        {option.statement}
                                    </div>
                                )
                            })
                        }
                    </div>
                    <div className={`gap-5 p-3 border rounded-md ${isSolutionVisible ? "grid" : "hidden"} ${isAnswerCorrect ? "bg-green-300" : "bg-red-200"}`}>
                        <h3 className="text-center font-semibold">
                            {
                                isAnswerCorrect ? "CORRECT ANSWER" : "WRONG ANSWER"
                            }
                        </h3>
                        <span>{data.solution.statement}</span>
                        <div className={`gap-3 ${isAnswerCorrect ? "hidden" : "grid"}`}>
                            <span className="font-semibold">Correct Option(s):</span>
                            <div className="w-full grid gap-2">
                                {
                                    data.options.map((option, index) => {
                                        return (
                                            <div key={index} className={`py-2 px-4 rounded-md border border-gray-500 ${option.isCorrect && "bg-green-400 border-green-700"}`}>
                                                {option.statement}
                                            </div>
                                        )
                                    })
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </CardContent>
            <CardFooter>
                <div className="w-full flex justify-between">
                    <Button
                        className={`${isSolutionVisible ? "hidden" : "flex"}`}
                        onClick={handleCheckAnswer}
                    >
                        Check Answer
                    </Button>
                    <div className="flex gap-3">
                        <Button
                            className="p-2"
                            onClick={handleSaveQuestion}
                        >
                            {
                                isProcessing ? (
                                    <Loader2 className="animate-spin" />
                                ) : (
                                    <Bookmark />
                                )
                            }
                        </Button>
                    </div>
                </div>
            </CardFooter>
        </Card>
    );
}