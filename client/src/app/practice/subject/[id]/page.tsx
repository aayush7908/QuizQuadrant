"use client"

import { InfiniteScroll } from "@/app/_components/InfiniteScroll";
import { Loader } from "@/app/_components/Loader";
import { QuestionCard } from "@/app/practice/_components/QuestionCard";
import { useToast } from "@/components/hooks/use-toast";
import { useEffect, useState } from "react";
import { getQuestionsBySubjectAction } from "../../_actions/get-questions-by-subject-action";
import Question from "@/app/_types/question";

export default function PracticeSubject({ params }: { params: { id: string } }) {

    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<Question[]>([]);
    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const { toast } = useToast();

    const fetchFunction = async (pageNumber: number) => {
        const { success, data, error } = await getQuestionsBySubjectAction(params.id, pageNumber);
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
        (async () => {
            const data = await fetchFunction(0);
            if (!data) {
                return;
            }
            setTotalLength(data[0].totalQuestions);
            const newInitialData = [] as Array<Question>;
            for (let i = 1; i < data.length; i++) {
                newInitialData.push({ ...data[i] });
            }
            setInitialData(newInitialData);
            setIsProcessing(false);
        })();
    }, []);

    return (
        <div className="py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
            {
                isProcessing ? (
                    <Loader />
                ) : (
                    <InfiniteScroll
                        fetchFunction={fetchFunction}
                        totalLength={totalLength}
                        initialData={initialData}
                        Component={QuestionCard}
                    />
                )
            }
        </div>
    );
}