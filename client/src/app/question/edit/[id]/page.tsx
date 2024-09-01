"use client"

import { getQuestionByIdAPI } from "@/actions/question/get/by-id";
import { updateQuestionAPI } from "@/actions/question/update";
import { Loader } from "@/components/custom/Loader";
import QuestionForm from "@/components/custom/question/QuestionForm";
import { useToast } from "@/components/ui/use-toast";
import { Question } from "@/lib/type/model/Question";
import { useEffect, useState } from "react";

export default function EditQuestion({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [question, setQuestion] = useState<Question | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: Question) => {
        return await updateQuestionAPI(data, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getQuestionByIdAPI(params.id);
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
                            successMessage="Question updated successfully"
                            onSubmit={onSubmit}
                            defaultFormData={question}
                        />
                    </div>
                )
            }
        </>
    );
}