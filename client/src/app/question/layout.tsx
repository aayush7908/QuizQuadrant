"use client"

import { SideMenu } from "@/components/custom/account/SideMenu";
import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react";

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
                user && user.isEmailVerified && (user.role === "TEACHER" || user.role === "ADMIN") ? (
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
