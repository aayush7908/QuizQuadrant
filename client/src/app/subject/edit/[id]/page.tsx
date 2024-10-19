"use client"

import { useEffect, useState } from "react";
import { z } from "zod";
import { useToast } from "@/components/hooks/use-toast";
import { Loader } from "@/app/_components/Loader";
import { SubjectForm } from "@/app/subject/_components/SubjectForm";
import { updateSubjectAction } from "../../_actions/update-subject-action";
import { getSubjectAction } from "../../_actions/get-subject-action";
import { SubjectFormSchema } from "../../_types/subject-form-schema";
import Subject from "@/app/_types/subject";
import SubjectRequest from "../../_types/subject-request";

export default function EditSubject({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [subject, setSubject] = useState<Subject | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: z.infer<typeof SubjectFormSchema>) => {
        const reqBody = {
            name: data.name
        } as SubjectRequest;
        return await updateSubjectAction(reqBody, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getSubjectAction(params.id);
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
                        <SubjectForm
                            successMessage="Subject Updated Successully"
                            onSubmit={onSubmit}
                            defaultFormData={subject}
                        />
                    </div>
                )
            }
        </>

    );
}