"use client"

import { Question } from "@/lib/type/model/question";
import { Loader2 } from "lucide-react";
import { Component, ReactNode, useEffect, useState } from "react";
import { useInView } from "react-intersection-observer";

export function QuestionInfiniteScroll({
    fetchFunction,
    totalLength,
    initialData,
    QuestionCard
}: {
    fetchFunction(pageNumber: number): Promise<Question[] | undefined>,
    totalLength: number,
    initialData: Array<Question>,
    QuestionCard: React.FC<{ question: Question; index: number; }>
}) {

    const [questions, setQuestions] = useState<Array<Question>>(initialData);
    const [pageNumber, setPageNumber] = useState<number>(1);
    const { ref, inView } = useInView();

    useEffect(() => {
        if (inView && (questions?.length || 0) < totalLength) {
            (async () => {
                const data = await fetchFunction(pageNumber);
                let newQuestions = questions ? [...questions] : [];
                newQuestions = newQuestions.concat(data || []);
                setQuestions(newQuestions);
                setPageNumber(pageNumber => pageNumber + 1);
            })();
        }
    }, [inView, totalLength]);

    return (
        <>
            {
                (questions && questions.length > 0) ? (
                    <>
                        {
                            questions.map((question, index) => {
                                return (
                                    <QuestionCard key={index} question={question} index={index} />
                                )
                            })
                        }
                    </>
                ) : (
                    <div className="h-full flex justify-center text-xl">No questions to display</div>
                )
            }
            <div className={`w-full flex gap-3 justify-center items-center ${questions.length === totalLength && "hidden"}`} ref={ref}>
                <Loader2 className="animate-spin size-5" />
                <span className="font-medium text-lg">Loading More ...</span>
            </div>
        </>
    );
}