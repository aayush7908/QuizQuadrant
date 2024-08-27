"use client"

import { getSubtopicByIdAPI } from "@/actions/subtopic/get/id";
import { updateSubtopicAPI } from "@/actions/subtopic/update";
import { Loader } from "@/components/custom/Loader";
import { SubtopicForm } from "@/components/custom/subtopic/SubtopicForm";
import { useToast } from "@/components/ui/use-toast";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { schema } from "@/lib/zod-schema/subtopic/subtopic";
import { useEffect, useState } from "react";
import { z } from "zod";

export default function EditSubtopic({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [subtopic, setSubtopic] = useState<Subtopic | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name,
            subject: {
                id: data.subject
            }
        } as Subtopic;
        return await updateSubtopicAPI(reqBody, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getSubtopicByIdAPI(params.id);
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
                        <SubtopicForm successMessage="Subtopic updated successully" onSubmit={onSubmit} defaultFormData={subtopic} />
                    </div>
                )
            }
        </>

    );
}