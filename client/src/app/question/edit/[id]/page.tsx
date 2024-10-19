"use client"

import { Loader } from "@/app/_components/Loader";
import Question from "@/app/_types/question";
import QuestionForm from "@/app/question/_components/QuestionForm";
import { useToast } from "@/components/hooks/use-toast";
import { useEffect, useState } from "react";
import { updateQuestionAction } from "../../_actions/update-question-action";
import { deleteQuestionAction } from "../../../_actions/delete-question-action";
import { getQuestionAction } from "../../_actions/get-question-action";
import QuestionRequest from "../../_types/question-request";

export default function EditQuestion({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [question, setQuestion] = useState<Question | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: QuestionRequest) => {
        return await updateQuestionAction(data, params.id);
    }

    const onDelete = async () => {
        return await deleteQuestionAction(params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getQuestionAction(params.id);
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
                            onDelete={onDelete}
                            defaultFormData={question}
                        />
                    </div>
                )
            }
        </>
    );
}