"use client"

import { getSubjectByIdAPI } from "@/actions/subject/get/id";
import { updateSubjectAPI } from "@/actions/subject/update";
import { Loader } from "@/components/custom/Loader";
import { SubjectForm } from "@/components/custom/subject/SubjectForm";
import { useToast } from "@/components/ui/use-toast";
import { Subject } from "@/lib/type/model/Subject";
import { schema } from "@/lib/zod-schema/subject/subject";
import { useEffect, useState } from "react";
import { z } from "zod";

export default function EditSubject({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [subject, setSubject] = useState<Subject | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name
        } as Subject;
        return await updateSubjectAPI(reqBody, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getSubjectByIdAPI(params.id);
            if (success && data) {
                setSubject(data);
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
                subject && (
                    <div className="h-full flex justify-center items-center">
                        <SubjectForm successMessage="Subject updated successully" onSubmit={onSubmit} defaultFormData={subject} />
                    </div>
                )
            }
        </>

    );
}