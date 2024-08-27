"use client"

import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Question } from "@/lib/type/model/question";
import { useEffect, useState } from "react";

export function QuestionCard({ question, index }: { question: Question, index: number }) {

    const [choice, setChoice] = useState<Map<string, boolean>>(new Map());
    const [isSolutionVisible, setIsSolutionVisible] = useState<boolean>(false);
    const [isAnswerCorrect, setIsAnswerCorrect] = useState<boolean>(false);

    const handleCheckAnswer = () => {
        let flag: boolean = true;
        question.options.forEach((option) => {
            flag = flag && (choice.get(option.id) === option.isCorrect);
        });
        setIsAnswerCorrect(flag);
        setIsSolutionVisible(true);
    }

    const handleOptionSelection = (id: string) => {
        if (isSolutionVisible) {
            return;
        }
        const newChoice = new Map(choice);
        if (question.type === "MSQ") {
            newChoice.set(id, !choice.get(id));
        } else {
            question.options.forEach((option) => {
                newChoice.set(option.id, option.id === id);
            });
        }
        setChoice(newChoice);
    }

    useEffect(() => {
        const newChoice = new Map<string, boolean>();
        question.options.forEach((option) => {
            newChoice.set(option.id, false);
        });
        setChoice(newChoice);
    }, []);

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
                        <Badge variant="secondary" className="text-base border-gray-400">{question.subtopic.subject.name}</Badge>
                        <Badge variant="secondary" className="text-base border-gray-400">{question.subtopic.name}</Badge>
                    </div>
                    <span>{question.statement}</span>
                    <div className="w-full grid gap-2">
                        {
                            question.options.map((option, index) => {
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
                        <span>{question.solution.statement}</span>
                        <div className={`gap-3 ${isAnswerCorrect ? "hidden" : "grid"}`}>
                            <span className="font-semibold">Correct Option(s):</span>
                            <div className="w-full grid gap-2">
                                {
                                    question.options.map((option, index) => {
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
                <div className={`w-full flex justify-center ${isSolutionVisible ? "hidden" : "flex"}`}>
                    <Button onClick={handleCheckAnswer}>Check Answer</Button>
                </div>
            </CardFooter>
        </Card>
    );
}