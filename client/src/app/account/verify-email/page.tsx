"use client"

import { useContext, useEffect, useState } from "react"
import { AuthContext } from "@/context/auth/AuthContext"
import { useRouter } from "next/navigation"
import { useToast } from "@/components/hooks/use-toast"
import VerifyEmailForm from "./_components/VerifyEmailForm"

export default function VerifyEmail() {

    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    useEffect(() => {
        if (!user || user.isEmailVerified) {
            toast({
                title: "Email already verified",
                variant: "destructive"
            });
            router.push("/");
        }
    }, []);

    return (
        <>
            {
                user && !user.isEmailVerified ? (
                    <VerifyEmailForm />
                ) : (
                    null
                )
            }
        </>
    )
}