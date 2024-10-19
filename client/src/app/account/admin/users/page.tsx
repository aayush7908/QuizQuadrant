"use client"

import { getAllUsersAction } from "@/app/account/admin/users/_actions/get-all-users-action";
import UserAccordion from "@/app/account/admin/users/_components/UserAccordion";
import { InfiniteScroll } from "@/app/_components/InfiniteScroll";
import { Loader } from "@/app/_components/Loader";
import { Accordion } from "@/components/ui/accordion";
import { AuthContext } from "@/context/auth/AuthContext";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import UserProfile from "@/app/_types/user-profile";
import { useToast } from "@/components/hooks/use-toast";
import { validateAdminAccess } from "@/app/_lib/user-access-validation-utils";

export default function Profile() {

    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<UserProfile[]>([]);
    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    const fetchFunction = async (pageNumber: number) => {
        const { success, data, error } = await getAllUsersAction(pageNumber);
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
        if (!validateAdminAccess(user)) {
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
            setTotalLength(data[0].totalUsers);
            const newInitialData = [] as UserProfile[];
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
                validateAdminAccess(user) && (
                    <div className="min-h-full p-[1rem] md:p-[2rem] lg:p-[3rem] pb-[3rem]">
                        {
                            isProcessing ? (
                                <Loader />
                            ) : (
                                <Accordion type="single" collapsible>
                                    <InfiniteScroll
                                        fetchFunction={fetchFunction}
                                        totalLength={totalLength}
                                        initialData={initialData}
                                        Component={UserAccordion}
                                    />
                                </Accordion>
                            )
                        }
                    </div>
                )
            }
        </>
    );
}