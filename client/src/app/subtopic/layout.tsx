"use client"

import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react";

export default function SubtopicLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    useEffect(() => {
        if (!user || user.role !== "ADMIN") {
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
                user && user.role === "ADMIN" ? (
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
