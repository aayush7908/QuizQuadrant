"use client"

import { getAllUsersAction } from "@/actions/account/admin/users/get-all-users-action";
import UserAccordion from "@/components/custom/account/admin/users/UserAccordion";
import { InfiniteScroll } from "@/components/custom/InfiniteScroll";
import { Loader } from "@/components/custom/Loader";
import { Accordion } from "@/components/ui/accordion";
import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { User } from "@/lib/type/model/User";
import { validateAdminAccess } from "@/lib/validation/validate-access";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";

export default function Profile() {

    const [isProcessing, setIsProcessing] = useState<boolean>(true);
    const [totalLength, setTotalLength] = useState<number>(0);
    const [initialData, setInitialData] = useState<Array<User>>([]);
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
            const newInitialData = [] as User[];
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