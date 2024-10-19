"use client"

import ConfirmForm from "@/app/account/delete-account/_components/ConfirmForm";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/components/hooks/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { validateUserAccess } from "@/app/_lib/user-access-validation-utils";
import { useRouter } from "next/navigation";
import { useContext, useEffect } from "react";

export default function DeleteAccount() {

    const { user } = useContext(AuthContext);
    const { toast } = useToast();
    const router = useRouter();

    useEffect(() => {
        if (!validateUserAccess(user)) {
            toast({
                title: "Access Deined",
                variant: "destructive"
            });
            router.push("/");
        }
    }, []);

    return (
        <>
            {
                validateUserAccess(user) ? (
                    <div className="h-full flex justify-center items-center">
                        <Card className="max-w-sm m-[1rem]">
                            <CardHeader>
                                <CardTitle className="text-2xl text-center">Are you sure you want to Delete Account ?</CardTitle>
                                <CardDescription>All your data will be deleted permanently and you will not be able retrieve them back again !!!</CardDescription>
                            </CardHeader>
                            <CardContent>
                                <div className="grid gap-4">
                                    <ConfirmForm />
                                </div>
                            </CardContent>
                        </Card >
                    </div>
                ) : (
                    null
                )
            }
        </>
    );
}