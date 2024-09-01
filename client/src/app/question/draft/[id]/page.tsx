"use client"

import { getDraftQuestionByIdAPI } from "@/actions/draft/question/get/by-id";
import { createQuestionAPI } from "@/actions/question/create";
import { Loader } from "@/components/custom/Loader";
import QuestionForm from "@/components/custom/question/QuestionForm";
import { useToast } from "@/components/ui/use-toast";
import { Question } from "@/lib/type/model/Question";
import { schema } from "@/lib/zod-schema/question/question";
import { useEffect, useState } from "react";
import { z } from "zod";

export default function DraftQuestion({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [question, setQuestion] = useState<Question | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: z.infer<typeof schema> | Question) => {
        data = data as Question;
        return await createQuestionAPI(data);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getDraftQuestionByIdAPI(params.id);
            if (success && data) {
                setQuestion(data);
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
            setIsProcessing(false);
        })();
    }, []);

    return (
        <>
            {
                isProcessing && (
                    <Loader />
                )
            }
            {
                question && (
                    <div className="py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
                        <QuestionForm
                            successMessage="Question created successfully"
                            onSubmit={onSubmit}
                            defaultFormData={question}
                        />
                    </div>
                )
            }
        </>
    );
}
