"use client"

import { getMyDraftExamsAPI } from "@/actions/draft/exam/get/my";
import { Loader } from "@/components/custom/Loader";
import { InfiniteScroll } from "@/components/custom/InfiniteScroll";
import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { Exam } from "@/lib/type/model/Exam";
import { PlusSquare } from "lucide-react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import { DraftExamCard } from "@/components/custom/draft/DraftExamCard";
import { getMyDraftQuestionsAPI } from "@/actions/draft/question/get/my";
import { Question } from "@/lib/type/model/Question";
import { DraftQuestionCard } from "@/components/custom/draft/DraftQuestionCard";

export default function DraftQuestions() {

    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<Array<Question>>([]);
    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const fetchFunction = async (pageNumber: number) => {
        const { success, data, error } = await getMyDraftQuestionsAPI(pageNumber);
        if (success && data) {
            return data;
        } else if (error) {
            toast({
                title: error.errorMessage,
                variant: "destructive"
            });
        }
    }

    useEffect(() => {
        if (!user || (!user.isEmailVerified && user.role !== "TEACHER" && user.role !== "ADMIN")) {
            toast({
                title: "Access Denied",
                variant: "destructive"
            });
            router.push("/");
            return;
        }
        (async () => {
            const data = await fetchFunction(0);
            if (!data) {
                return;
            }
            setTotalLength(data[0].createdBy.totalDraftQuestions);
            const newInitialData = [] as Array<Question>;
            for (let i = 1; i < data.length; i++) {
                newInitialData.push({ ...data[i] });
            }
            setInitialData(newInitialData);
            setIsProcessing(false);
        })();
    }, []);

    return (
        <>
            {
                user && user.isEmailVerified && (user.role === "TEACHER" || user.role === "ADMIN") ? (
                    <div className="p-[1rem] md:p-[2rem] lg:p-[3rem] pb-[3rem] grid gap-5">
                        <div className="flex justify-end">
                            <Link href={"/question/create"} className="h-[2.5rem] flex items-center gap-2 text-base bg-black text-white py-2 px-3 rounded-md cursor-pointer">
                                <PlusSquare />
                                <span>Create Question</span>
                            </Link>
                        </div>
                        {
                            isProcessing ? (
                                <Loader />
                            ) : (
                                <InfiniteScroll
                                    fetchFunction={fetchFunction}
                                    totalLength={totalLength}
                                    initialData={initialData}
                                    Component={DraftQuestionCard}
                                />
                            )
                        }
                    </div>
                ) : (
                    null
                )
            }
        </>
    );
}