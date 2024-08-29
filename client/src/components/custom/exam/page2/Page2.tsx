"use client"

import { useToast } from "@/components/ui/use-toast";
import QuestionForm from "./QuestionForm";
import { SideMenu } from "./SideMenu";
import { Exam } from "@/lib/type/model/Exam";
import { Button } from "@/components/ui/button";
import { FileSearch2, SquareArrowLeft, Trash2 } from "lucide-react";
import { useState } from "react";
import { SelectQuestion } from "./SelectQuestion";

export function Page2({
    questionIndex,
    changeQuestionIndex,
    exam,
    changeExam
}: {
    questionIndex: number,
    changeQuestionIndex: Function,
    exam: Exam,
    changeExam: Function
}) {

    const [isSelectMenuOpen, setIsSelectMenuOpen] = useState<boolean>(false);
    const { toast } = useToast();

    const toggleSelectMenuOpen = () => {
        setIsSelectMenuOpen(isSelectMenuOpen => !isSelectMenuOpen);
    }

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
        if (exam.questions.length === 1) {
            toast({
                title: "Atleast one question is required",
                variant: "destructive"
            });
            return;
        }
        const newExam = { ...exam } as Exam;
        newExam.questions = exam.questions.filter((question, index) => {
            return (index !== questionIndex);
        });
        changeExam(newExam);
        changeQuestionIndex(questionIndex - 1);
        toast({
            title: "Question deleted successfully"
        });
    }

    return (
        <div className="min-h-full w-full flex">
            <SideMenu
                questionIndex={questionIndex}
                changeQuestionIndex={changeQuestionIndex}
                exam={exam}
                changeExam={changeExam}
            />
            <div className="w-full md:w-[calc(100%-17rem)] p-[1rem] md:p-[2rem] lg:p-[3rem] md:ms-[17rem] mb-[4rem]">
                {
                    isSelectMenuOpen ? (
                        <>
                            <div className="flex gap-3 mb-2">
                                <Button
                                    onClick={toggleSelectMenuOpen}
                                    className="px-2"
                                >
                                    <SquareArrowLeft />
                                </Button>
                            </div>
                            <SelectQuestion
                                questionIndex={questionIndex}
                                exam={exam}
                                changeExam={changeExam}
                                toggleSelectMenuOpen={toggleSelectMenuOpen}
                            />
                        </>
                    ) : (
                        <>
                            <div className="flex gap-3 justify-between items-center mb-2">
                                <div>
                                    <p className="py-2 px-3 text-lg font-semibold bg-muted border rounded-md">Question - {questionIndex + 1}</p>
                                </div>
                                <div className="flex gap-3 justify-end mb-2">
                                    <Button
                                        onClick={toggleSelectMenuOpen}
                                        className="px-2"
                                    >
                                        <FileSearch2 />
                                    </Button>
                                    <Button
                                        variant="destructive"
                                        onClick={handleDeleteQuestion}
                                        className="px-2"
                                    >
                                        <Trash2 />
                                    </Button>
                                </div>
                            </div>
                            <QuestionForm
                                questionIndex={questionIndex}
                                exam={exam}
                                changeExam={changeExam}
                            />
                        </>
                    )
                }
            </div>
        </div>
    );
}