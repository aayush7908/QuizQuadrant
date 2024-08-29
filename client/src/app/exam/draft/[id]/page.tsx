"use client"

import { getDraftExamByIdAPI } from "@/actions/draft/exam/get/by-id";
import { ExamForm } from "@/components/custom/exam/ExamForm";
import { Loader } from "@/components/custom/Loader";
import { useToast } from "@/components/ui/use-toast";
import { Exam } from "@/lib/type/model/Exam";
import { useEffect, useState } from "react";

export default function CreateExam({ params }: { params: { id: string } }) {

    const [isProcessing, setIsProcessing] = useState<boolean>(false);
    const [exam, setExam] = useState<Exam | undefined>(undefined);
    const { toast } = useToast();

    useEffect(() => {
        (async () => {
            setIsProcessing(true);
            const { success, data, error } = await getDraftExamByIdAPI(params.id);
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
                    <ExamForm defaultFormData={exam} />
                )
            }
        </>
    );
}
