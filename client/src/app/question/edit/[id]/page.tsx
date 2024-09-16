"use client"

import { getQuestionByIdAction } from "@/actions/question/edit/get-question-by-id-action";
import { updateQuestionAction } from "@/actions/question/edit/update-question-action";
import { Loader } from "@/components/custom/Loader";
import QuestionForm from "@/components/custom/question/QuestionForm";
import { useToast } from "@/components/ui/use-toast";
import { Question } from "@/lib/type/model/Question";
import { req } from "@/lib/type/request/question/question-form-request";
import { useEffect, useState } from "react";
import { deleteQuestionAction } from "@/actions/question/edit/delete-question-action";

export default function EditQuestion({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [question, setQuestion] = useState<Question | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: req) => {
        return await updateQuestionAction(data, params.id);
    }

    const onDelete = async () => {
        return await deleteQuestionAction(params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getQuestionByIdAction(params.id);
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