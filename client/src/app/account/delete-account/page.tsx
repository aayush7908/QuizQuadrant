"use client"

import ConfirmForm from "@/components/custom/account/delete-account/ConfirmForm";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";

export default function DeleteAccount() {

    const onSubmit = async () => {
        alert("delete-account");
    }

    return (
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
    );
}