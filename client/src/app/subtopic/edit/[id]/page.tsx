"use client"

import { useEffect, useState } from "react";
import { z } from "zod";
import { useToast } from "@/components/hooks/use-toast";
import { Loader } from "@/app/_components/Loader";
import { SubtopicForm } from "@/app/subtopic/_components/SubtopicForm";
import { updateSubtopicAction } from "../../_actions/update-subtopic-action";
import { getSubtopicAction } from "../../_actions/get-subtopic-action";
import Subtopic from "@/app/_types/subtopic";
import { SubtopicFormSchema } from "../../_types/subtopic-form-schema";
import SubtopicRequest from "../../_types/subtopic-request";

export default function EditSubtopic({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [subtopic, setSubtopic] = useState<Subtopic | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: z.infer<typeof SubtopicFormSchema>) => {
        const reqBody = {
            name: data.name,
            subjectId: data.subject
        } as SubtopicRequest;
        return await updateSubtopicAction(reqBody, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getSubtopicAction(params.id);
            if (success && data) {
                setSubtopic(data);
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
                subtopic && (
                    <div className="h-full flex justify-center items-center">
                        <SubtopicForm
                            successMessage="Subtopic Updated Successully"
                            onSubmit={onSubmit}
                            defaultFormData={subtopic}
                        />
                    </div>
                )
            }
        </>

    );
}