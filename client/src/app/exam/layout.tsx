"use client"

import { SideMenu } from "@/components/custom/account/SideMenu";
import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react";

export default function ExamLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    useEffect(() => {
        if (!user) {
            toast({
                title: "Access Denied",
                variant: "destructive"
            });
            router.push("/");
        }
    }, [user]);

    return (
        <>
            {children}
        </>
    );
}
