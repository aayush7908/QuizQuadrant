"use client"

import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react";
import { useToast } from "@/components/hooks/use-toast";
import { SideMenu } from "./_components/SideMenu";
import { AuthContext } from "@/context/auth/AuthContext";

export default function AccountLayout({
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
    }, []);

    return (
        <>
            {
                user ? (
                    <div className="h-full w-full flex">
                        <SideMenu />
                        <div className="h-full w-full md:w-[calc(100%-17rem)] pb-[3rem] md:ms-[17rem] md:pb-0">
                            {children}
                        </div>
                    </div>
                ) : (
                    null
                )
            }
        </>
    );
}
