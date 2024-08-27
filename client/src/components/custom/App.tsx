"use client"

import { authenticateAPI } from "@/actions/auth/authenticate";
import { AuthContext } from "@/context/auth/AuthContext";
import { useContext, useEffect, useState } from "react";
import { useToast } from "../ui/use-toast";
import { getAllSubjectsAPI } from "@/actions/subject/get/all";
import { SubjectContext } from "@/context/subject/SubjectContext";
import { Loader } from "./Loader";

export default function App({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const [isAuthenticating, setIsAuthenticating] = useState<boolean>(true);
    const { authenticate } = useContext(AuthContext);
    const { addSubjects } = useContext(SubjectContext);
    const { toast } = useToast();

    useEffect(() => {
        (async () => {
            const { success, data, error } = await authenticateAPI();
            if (success && data) {
                authenticate(data.user);
                toast({
                    title: "User authenticated successfully"
                });
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
            setIsAuthenticating(false);
        })();
        (async () => {
            const { success, data, error } = await getAllSubjectsAPI();
            if (success && data) {
                addSubjects(data);
            } else if (error) {
                toast({
                    title: error.errorMessage,
                    variant: "destructive"
                });
            }
        })();
    }, []);

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