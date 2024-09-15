"use client"

import ConfirmForm from "@/components/custom/account/delete-account/ConfirmForm";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/components/ui/use-toast";
import { AuthContext } from "@/context/auth/AuthContext";
import { validateUserAccess } from "@/lib/validation/validate-access";
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