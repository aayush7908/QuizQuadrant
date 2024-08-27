"use client"

import { getMyQuestionsAPI } from "@/actions/question/get/my";
import { QuestionCard } from "@/components/custom/account/questions-created/QuestionCard";
import { useToast } from "@/components/ui/use-toast";
import { Question } from "@/lib/type/model/question";
import { PlusSquare } from "lucide-react";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function QuestionsCreated() {

    const [questions, setQuestions] = useState<Array<Question> | undefined>(undefined);
    const { toast } = useToast();

    useEffect(() => {
        (async () => {
            const { success, data, error } = await getMyQuestionsAPI();
            if (success && data) {
                setQuestions(data);
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
        })();
    }, []);

    return (
        <div className="p-[1rem] md:p-[2rem] lg:p-[3rem] pb-[3rem] grid gap-5">
            <div className="flex justify-end">
                <Link href={"/question/create"} className="h-[2.5rem] flex items-center gap-2 text-base bg-black text-white py-2 px-3 rounded-md cursor-pointer">
                    <PlusSquare />
                    <span>Create Question</span>
                </Link>
            </div>
            {
                (questions && questions.length > 0) ? (
                    questions.map((question: Question, index: number) => {
                        return (
                            <QuestionCard key={index} index={index} question={question} />
                        )
                    })
                ) : (
                    <div className="h-full flex justify-center text-xl">No questions created</div>
                )
            }
        </div>
    );
}