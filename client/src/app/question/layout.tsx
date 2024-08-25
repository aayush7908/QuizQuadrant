"use client"

import { SideMenu } from "@/components/custom/account/SideMenu";
import { AuthContext } from "@/context/auth/AuthContext";
import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react";

export default function QuestionLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const { user } = useContext(AuthContext);
    const router = useRouter();

    useEffect(() => {
        if (!user) {
            router.push("/");
        }
    }, [user]);

    return (
        <>
            {children}
        </>
    );
}
