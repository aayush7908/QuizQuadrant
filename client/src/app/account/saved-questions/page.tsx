"use client"

import { QuestionCard } from "@/app/account/saved-questions/_components/QuestionCard";
import { InfiniteScroll } from "@/app/_components/InfiniteScroll";
import { Loader } from "@/app/_components/Loader";
import { AuthContext } from "@/context/auth/AuthContext";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import { useToast } from "@/components/hooks/use-toast";
import { getMySavedQuestionsAction } from "./_actions/get-my-saved-questions-action";
import { validateUserAccess } from "@/app/_lib/user-access-validation-utils";
import Question from "@/app/_types/question";

export default function SavedQuestions() {

    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<Array<Question>>([]);
    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const fetchFunction = async (pageNumber: number) => {
        const { success, data, error } = await getMySavedQuestionsAction(pageNumber);
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
        if (!validateUserAccess(user)) {
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
            setTotalLength(data[0].totalQuestions);
            const newInitialData = [] as Question[];
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
                validateUserAccess(user) ? (
                    <div className="p-[1rem] md:p-[2rem] lg:p-[3rem] pb-[3rem] grid gap-5">
                        {
                            isProcessing ? (
                                <Loader />
                            ) : (
                                <InfiniteScroll
                                    fetchFunction={fetchFunction}
                                    totalLength={totalLength}
                                    initialData={initialData}
                                    Component={QuestionCard}
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