"use client"

import { getSubjectByIdAction } from "@/actions/subject/get/get-subject-action";
import { updateSubjectAction } from "@/actions/subject/edit/update-subject-action";
import { Loader } from "@/components/custom/Loader";
import { SubjectForm } from "@/components/custom/subject/SubjectForm";
import { useToast } from "@/components/ui/use-toast";
import { Subject } from "@/lib/type/model/Subject";
import { useEffect, useState } from "react";
import { z } from "zod";
import { schema } from "@/lib/zod-schema/subject/subject-form-schema";
import { req } from "@/lib/type/request/subject/subject-form-request";

export default function EditSubject({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [subject, setSubject] = useState<Subject | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: z.infer<typeof schema>) => {
        const reqBody = {
            name: data.name
        } as req;
        return await updateSubjectAction(reqBody, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getSubjectByIdAction(params.id);
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