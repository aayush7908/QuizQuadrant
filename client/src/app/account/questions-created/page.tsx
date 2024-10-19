"use client"

import { QuestionCard } from "@/app/account/questions-created/_components/QuestionCard";
import { InfiniteScroll } from "@/app/_components/InfiniteScroll";
import { Loader } from "@/app/_components/Loader";
import { AuthContext } from "@/context/auth/AuthContext";
import { PlusSquare } from "lucide-react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import Question from "@/app/_types/question";
import { useToast } from "@/components/hooks/use-toast";
import { getMyQuestionsAction } from "./_actions/get-my-created-questions-action";
import { validateTeacherAccess } from "@/app/_lib/user-access-validation-utils";

export default function QuestionsCreated() {

    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<Question[]>([]);
    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const fetchFunction = async (pageNumber: number) => {
        const { success, data, error } = await getMyQuestionsAction(pageNumber);
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
        if (!validateTeacherAccess(user)) {
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
                validateTeacherAccess(user) ? (
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