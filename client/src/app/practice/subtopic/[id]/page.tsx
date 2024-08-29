"use client"

import { getQuestionsBySubtopicAPI } from "@/actions/question/get/by-subtopic";
import { InfiniteScroll } from "@/components/custom/InfiniteScroll";
import { Loader } from "@/components/custom/Loader";
import { QuestionCard } from "@/components/custom/practice/QuestionCard";
import { useToast } from "@/components/ui/use-toast";
import { Question } from "@/lib/type/model/Question";
import { useEffect, useState } from "react";

export default function PracticeSubtopic({ params }: { params: { id: string } }) {

    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<Array<Question>>([]);
    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const { toast } = useToast();

    const fetchFunction = async (pageNumber: number) => {
        const { success, data, error } = await getQuestionsBySubtopicAPI(params.id, pageNumber);
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
            setTotalLength(data[0].subtopic.totalQuestions);
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