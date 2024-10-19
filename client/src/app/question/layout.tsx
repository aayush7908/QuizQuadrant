"use client"

import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react";
import { useToast } from "@/components/hooks/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";

export default function QuestionLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    useEffect(() => {
        if (!user || (!user.isEmailVerified && user.role !== "TEACHER" && user.role !== "ADMIN")) {
            toast({
                title: "Access Denied",
                variant: "destructive"
            });
            router.push("/");
        }
    }, []);

    return (
        <>
            {
                user && (
                    (user.role === "TEACHER" && user.isEmailVerified) ||
                    user.role === "ADMIN"
                ) ? (
                    <>
                        {children}
                    </>
                ) : (
                    null
                )
            }
        </>
    );
}
