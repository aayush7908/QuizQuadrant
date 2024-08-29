"use client"

import { Exam } from "@/lib/type/model/Exam";
import { useEffect, useState } from "react";
import { Page1 } from "./page1/Page1";
import { Loader } from "../Loader";
import { Button } from "@/components/ui/button";
import { FileText, Loader2, SquareChevronLeft, SquareChevronRight } from "lucide-react";
import { Page2 } from "./page2/Page2";
import { Question } from "@/lib/type/model/Question";
import { Solution } from "@/lib/type/model/Solution";
import { Option } from "@/lib/type/model/Option";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { User } from "@/lib/type/model/User";
import { Page3 } from "./page3/Page3";
import { Subject } from "@/lib/type/model/Subject";
import { createExamDraftAPI } from "@/actions/draft/exam/create";
import { updateExamDraftAPI } from "@/actions/draft/exam/update";
import { useToast } from "@/components/ui/use-toast";

export function ExamForm({ defaultFormData }: { defaultFormData: Exam | undefined }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [isDraftSaved, setIsDraftSaved] = useState<boolean>(false);
    const [page, setPage] = useState<number>(1);
    const [questionIndex, setQuestionIndex] = useState<number>(0);
    const [exam, setExam] = useState<Exam | undefined>(undefined);
    const { toast } = useToast();

    const changeExam = (newExam: Exam) => {
        setExam(newExam);
        setIsDraftSaved(false);
    }

    const changeQuestionIndex = (newQuestionIndex: number) => {
        setQuestionIndex(newQuestionIndex);
    }

    const handleSaveDraft = async () => {
        if (exam) {
            setIsProcessing(true);
            let res;
            if (exam.id) {
                res = await updateExamDraftAPI(exam, exam.id);
            } else {
                res = await createExamDraftAPI(exam);
                const newExam = { ...exam } as Exam;
                if (res.data) {
                    newExam.id = res.data.id;
                    changeExam(newExam);
                }
            }
            if (res.success && res.data) {
                toast({
                    title: "Draft saved successfully"
                });
            } else if (res.error) {
                toast({
                    title: res.error.errorMessage,
                    variant: "destructive"
                });
            }
            setIsProcessing(false);
            setIsDraftSaved(true);
        }
    }

    useEffect(() => {
        const newExam = defaultFormData ? {
            id: defaultFormData.id,
            title: defaultFormData.title,
            startDateTime: new Date(defaultFormData.startDateTime),
            durationInMinutes: defaultFormData.durationInMinutes,
            questions: defaultFormData.questions,
            candidates: defaultFormData.candidates
        } as Exam : {
            title: "",
            startDateTime: new Date(),
            durationInMinutes: 0,
            questions: [
                {
                    id: undefined,
                    type: "",
                    positiveMarks: 0,
                    negativeMarks: 0,
                    statement: "",
                    imageUrl: undefined,
                    subtopic: {
                        id: "",
                        subject: {
                            id: ""
                        } as Subject
                    } as Subtopic,
                    options: [
                        {
                            statement: "",
                            imageUrl: undefined,
                            isCorrect: false
                        } as Option,
                        {
                            statement: "",
                            imageUrl: undefined,
                            isCorrect: false
                        } as Option,
                        {
                            statement: "",
                            imageUrl: undefined,
                            isCorrect: false
                        } as Option,
                        {
                            statement: "",
                            imageUrl: undefined,
                            isCorrect: false
                        } as Option
                    ],
                    solution: {
                        statement: "",
                        imageUrl: undefined
                    } as Solution
                } as Question
            ],
            candidates: [] as User[]
        } as Exam;
        setExam(newExam);
    }, [defaultFormData]);

    return (
        <div className="h-full w-full">
            {
                exam ? (
                    <>
                        <div className="h-[calc(100%-4rem)]">
                            {
                                page === 1 && (
                                    <div className="h-full py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
                                        <Page1
                                            exam={exam}
                                            changeExam={changeExam}
                                        />
                                    </div>
                                )
                            }
                            {
                                page === 2 && (
                                    <div className="h-full">
                                        <Page2
                                            questionIndex={questionIndex}
                                            changeQuestionIndex={changeQuestionIndex}
                                            exam={exam}
                                            changeExam={changeExam}
                                        />
                                    </div>
                                )
                            }
                            {
                                page === 3 && (
                                    <div className="h-full py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
                                        <Page3
                                            exam={exam}
                                            changeExam={changeExam}
                                        />
                                    </div>
                                )
                            }
                        </div>
                        <div className="fixed h-[4rem] w-full px-5 flex gap-3 justify-end md:justify-between items-center bg-muted border-t-2">
                            <div>
                                <Button
                                    className="px-2 sm:px-3"
                                    disabled={isDraftSaved}
                                    onClick={handleSaveDraft}
                                >
                                    {
                                        isProcessing ? (
                                            <Loader2 className="animate-spin" />
                                        ) : (
                                            <>
                                                <FileText className="sm:hidden" />
                                                <span className="hidden sm:block">Save as Draft</span>
                                            </>
                                        )
                                    }
                                </Button>
                            </div>
                            <div className="flex gap-3">
                                <Button
                                    className="px-2 sm:px-3"
                                    disabled={page === 1}
                                    onClick={() => {
                                        setPage(page => (page - 1));
                                    }}
                                >
                                    <SquareChevronLeft className="sm:hidden" />
                                    <span className="hidden sm:block">Prev</span>
                                </Button>
                                <Button
                                    className="px-2 sm:px-3"
                                    disabled={page === 3}
                                    onClick={() => {
                                        setPage(page => (page + 1));
                                    }}
                                >
                                    <SquareChevronRight className="sm:hidden" />
                                    <span className="hidden sm:block">Next</span>
                                </Button>
                            </div>
                        </div>
                    </>
                ) : (
                    <Loader />
                )
            }
        </div>
    );
}