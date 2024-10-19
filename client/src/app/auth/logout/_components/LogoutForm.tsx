"use client"

import { useRouter } from "next/navigation";
import { useContext } from "react"
import {
    Card,
    CardContent,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { AuthContext } from "@/context/auth/AuthContext";
import { removeToken } from "@/app/_lib/cookie-utils";

export default function LogoutForm() {

    const { logout } = useContext(AuthContext);
    const router = useRouter();

    const onSubmit = async () => {
        removeToken();
        logout();
        router.push("/");
    }

    return (
        <Card className="max-w-sm m-[1rem]">
            <CardHeader>
                <CardTitle className="text-2xl text-center">
                    Are you sure you want to Logout ?
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="grid gap-4">
                    <div className="grid gap-2">
                    </div>
                </div>
                <Button type="submit" onClick={onSubmit} className="w-full">
                    Logout
                </Button>
            </CardContent>
        </Card >
    )
}
