"use client"

import { getSubtopicByIdAction } from "@/actions/subtopic/get/get-subtopic-action";
import { updateSubtopicAction } from "@/actions/subtopic/edit/update-subtopic-action";
import { Loader } from "@/components/custom/Loader";
import { SubtopicForm } from "@/components/custom/subtopic/SubtopicForm";
import { useToast } from "@/components/ui/use-toast";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { useEffect, useState } from "react";
import { z } from "zod";
import { schema } from "@/lib/zod-schema/subtopic/subtopic-form-schema";
import { req } from "@/lib/type/request/subtopic/subtopic-form-request";

export default function EditSubtopic({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [subtopic, setSubtopic] = useState<Subtopic | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name,
            subjectId: data.subject
        } as req;
        return await updateSubtopicAction(reqBody, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getSubtopicByIdAction(params.id);
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