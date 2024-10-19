"use client"

import { authenticateAction } from "@/app/_actions/authenticate-user-action";
import { AuthContext } from "@/context/auth/AuthContext";
import { useContext, useEffect, useState } from "react";
import { useToast } from "@/components/hooks/use-toast";
import { getAllSubjectsAction } from "@/app/_actions/get-all-subject-action";
import { SubjectContext } from "@/context/subject/SubjectContext";
import { Loader } from "@/app/_components/Loader";
import { RefreshContext } from "@/context/refresh/RefreshContext";

export default function App({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const [isAuthenticating, setIsAuthenticating] = useState<boolean>(true);
    const { user, authenticate } = useContext(AuthContext);
    const { addSubjects } = useContext(SubjectContext);
    const { x } = useContext(RefreshContext);
    const { toast } = useToast();

    useEffect(() => {
        (async () => {
            const { success, data, error } = await authenticateAction();
            if (success && data) {
                authenticate(data.user);
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
            setIsAuthenticating(false);
        })();
        (async () => {
            const { success, data, error } = await getAllSubjectsAction();
            if (success && data) {
                addSubjects(data);
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
        })();
    }, [x]);

    return (
        <>
            {
                isAuthenticating ? (
                    <div className="h-full pt-[4rem] flex justify-center items-center text-xl">
                        <Loader />
                    </div>
                ) : (
                    <div className="h-full pt-[4rem]">
                        {children}
                    </div>
                )
            }
        </>
    );
}