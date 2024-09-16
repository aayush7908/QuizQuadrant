"use client"

import { getDraftQuestionByIdAction } from "@/actions/question/draft/get-draft-action";
import { createQuestionAction } from "@/actions/question/create/create-question-action";
import { updateDraftQuestionAction } from "@/actions/question/draft/update-draft-action";
import { Loader } from "@/components/custom/Loader";
import QuestionForm from "@/components/custom/question/QuestionForm";
import { useToast } from "@/components/ui/use-toast";
import { Question } from "@/lib/type/model/Question";
import { req } from "@/lib/type/request/question/question-form-request";
import { useEffect, useState } from "react";
import { deleteDraftQuestionAction } from "@/actions/question/draft/delete-draft-action";

export default function DraftQuestion({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [question, setQuestion] = useState<Question | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: req) => {
        return await createQuestionAction(data, params.id);
    }

    const onSubmitDraft = async (data: req) => {
        return await updateDraftQuestionAction(data, params.id);
    }

    const onDelete = async () => {
        return await deleteDraftQuestionAction(params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getDraftQuestionByIdAction(params.id);
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
                            onSubmitDraft={onSubmitDraft}
                            onDelete={onDelete}
                            defaultFormData={question}
                        />
                    </div>
                )
            }
        </>
    );
}
