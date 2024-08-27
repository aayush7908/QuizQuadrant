"use client"

import { getQuestionByIdAPI } from "@/actions/question/get/id";
import { updateQuestionAPI } from "@/actions/question/update";
import { Loader } from "@/components/custom/Loader";
import QuestionForm from "@/components/custom/question/QuestionForm";
import { useToast } from "@/components/ui/use-toast";
import { Option } from "@/lib/type/model/Option";
import { Question } from "@/lib/type/model/question";
import { Solution } from "@/lib/type/model/Solution";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { schema } from "@/lib/zod-schema/question/question";
import { useEffect, useState } from "react";
import { z } from "zod";

export default function EditQuestion({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [question, setQuestion] = useState<Question | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (formData: z.infer<typeof schema>) => {
        const reqBody = {
            type: formData.type,
            isPublic: formData.visibility === "PUBLIC",
            statement: formData.statement,
            imageUrl: formData.imageUrl,
            subtopic: {
                id: formData.subtopic,
            } as Subtopic,
            options: [
                {
                    statement: formData.options[0].statement,
                    imageUrl: formData.options[0].imageUrl,
                    isCorrect: formData.options[0].isCorrect
                } as Option,
                {
                    statement: formData.options[1].statement,
                    imageUrl: formData.options[1].imageUrl,
                    isCorrect: formData.options[1].isCorrect
                } as Option,
                {
                    statement: formData.options[2].statement,
                    imageUrl: formData.options[2].imageUrl,
                    isCorrect: formData.options[2].isCorrect
                } as Option,
                {
                    statement: formData.options[3].statement,
                    imageUrl: formData.options[3].imageUrl,
                    isCorrect: formData.options[3].isCorrect
                } as Option
            ],
            solution: {
                statement: formData.solution.statement,
                imageUrl: formData.solution.imageUrl
            } as Solution
        } as Question;
        console.log(reqBody);
        const res = await updateQuestionAPI(reqBody, params.id);
        return res;
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
                        <QuestionForm successMessage="Question updated successfully" onSubmit={onSubmit} defaultFormData={question} />
                    </div>
                )
            }
        </>
    );
}