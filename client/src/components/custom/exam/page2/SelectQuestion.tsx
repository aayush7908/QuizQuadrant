"use client"

import { getMyQuestionsAPI } from "@/actions/question/get/my";
import { InfiniteScroll } from "@/components/custom/InfiniteScroll";
import { Loader } from "@/components/custom/Loader";
import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { Question } from "@/lib/type/model/Question";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import { SelectQuestionCard } from "./SelectQuestionCard";
import { Exam } from "@/lib/type/model/Exam";

export function SelectQuestion({
    questionIndex,
    exam,
    changeExam,
    toggleSelectMenuOpen
}: {
    questionIndex: number,
    exam: Exam,
    changeExam: Function,
    toggleSelectMenuOpen: Function
}) {

    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<Array<Question>>([]);
    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const handleSelectQuestion = (question: Question) => {
        const newExam = { ...exam } as Exam;
        newExam.questions[questionIndex] = { ...question };
        changeExam(newExam);
        toggleSelectMenuOpen();
    }

    const fetchFunction = async (pageNumber: number) => {
        const { success, data, error } = await getMyQuestionsAPI(pageNumber);
        if (success && data) {
            return data;
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
    }

    useEffect(() => {
        if (!user || (!user.isEmailVerified && user.role !== "TEACHER" && user.role !== "ADMIN")) {
            toast({
                title: "Access Denied",
                variant: "destructive"
            });
            router.push("/");
            return;
        }
        (async () => {
            const data = await fetchFunction(0);
            if (!data) {
                return;
            }
            setTotalLength(data[0].createdBy.totalQuestions);
            const newInitialData = [] as Array<Question>;
            for (let i = 1; i < data.length; i++) {
                newInitialData.push({ ...data[i] });
            }
            setInitialData(newInitialData);
            setIsProcessing(false);
        })();
    }, []);

    return (
        <>
            {
                user && user.isEmailVerified && (user.role === "TEACHER" || user.role === "ADMIN") ? (
                    <div className="grid gap-5">
                        {
                            isProcessing ? (
                                <Loader />
                            ) : (
                                <InfiniteScroll
                                    fetchFunction={fetchFunction}
                                    totalLength={totalLength}
                                    initialData={initialData}
                                    Component={SelectQuestionCard}
                                    componentDefaultProps={handleSelectQuestion}
                                />
                            )
                        }
                    </div>
                ) : (
                    null
                )
            }
        </>
    );
}