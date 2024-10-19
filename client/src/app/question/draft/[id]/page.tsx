"use client"

import { useEffect, useState } from "react";
import { useToast } from "@/components/hooks/use-toast";
import { Loader } from "@/app/_components/Loader";
import QuestionForm from "@/app/question/_components/QuestionForm";
import { createQuestionAction } from "../../_actions/create-question-action";
import { updateDraftQuestionAction } from "../../_actions/update-draft-action";
import { deleteDraftQuestionAction } from "../../../_actions/delete-draft-action";
import { getDraftQuestionAction } from "../../_actions/get-draft-action";
import Question from "@/app/_types/question";
import QuestionRequest from "../../_types/question-request";

export default function DraftQuestion({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [question, setQuestion] = useState<Question | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: QuestionRequest) => {
        return await createQuestionAction(data, params.id);
    }

    const onSubmitDraft = async (data: QuestionRequest) => {
        return await updateDraftQuestionAction(data, params.id);
    }

    const onDelete = async () => {
        return await deleteDraftQuestionAction(params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getDraftQuestionAction(params.id);
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
