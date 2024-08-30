"use client"

import { getExamByIdAPI } from "@/actions/exam/get/by-id";
import { updateExamAPI } from "@/actions/exam/update";
import { ExamForm } from "@/components/custom/exam/ExamForm";
import { Loader } from "@/components/custom/Loader";
import { useToast } from "@/components/ui/use-toast";
import { Exam } from "@/lib/type/model/Exam";
import { useEffect, useState } from "react";

export default function EditExam({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [exam, setExam] = useState<Exam | undefined>(undefined);
    const { toast } = useToast();

    const onSubmit = async (data: Exam) => {
        return await updateExamAPI(data, params.id);
    }

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getExamByIdAPI(params.id);
            if (success && data) {
                setExam(data);
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
                exam && (
                    <ExamForm
                        successMessage="Exam saved successfully"
                        onSubmit={onSubmit}
                        defaultFormData={exam}
                    />
                )
            }
        </>
    );
}
