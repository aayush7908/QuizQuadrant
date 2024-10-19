"use client"

import { unsaveQuestionAction } from "@/app/_actions/unsave-question-action";
import Option from "@/app/_types/option";
import Question from "@/app/_types/question";
import { useToast } from "@/components/hooks/use-toast";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Bookmark, BookmarkX, Loader2 } from "lucide-react";
import { useEffect, useState } from "react";

export function QuestionCard({ data, index, removeFunction }: { data: Question, index: number, removeFunction(index: number): void }) {

    const [choice, setChoice] = useState<Map<string, boolean>>(new Map());
    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [isAnswerCorrect, setIsAnswerCorrect] = useState<boolean>(false);
    const [isSolutionVisible, setIsSolutionVisible] = useState<boolean>(false);
    const { toast } = useToast();

    const handleCheckAnswer = () => {
        let flag: boolean = true;
        data.options.forEach((option: Option) => {
            flag = flag && (choice.get(option.id) === option.isCorrect);
        });
        setIsAnswerCorrect(flag);
        setIsSolutionVisible(true);
    }

    const handleSaveQuestion = async () => {
        setIsProcessing(true);
        const { success, error } = await unsaveQuestionAction(data.id);
        if (success) {
            toast({
                title: "Question unsaved successfully"
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
                        className="p-2"
                        onClick={handleSaveQuestion}
                    >
                        {
                            isProcessing ? (
                                <Loader2 className="animate-spin" />
                            ) : (
                                <BookmarkX />
                            )
                        }
                    </Button>
                    <Button
                        className={`${isSolutionVisible ? "hidden" : "flex"}`}
                        onClick={handleCheckAnswer}
                    >
                        Check Answer
                    </Button>
                </div>
            </CardFooter>
        </Card>
    );
}