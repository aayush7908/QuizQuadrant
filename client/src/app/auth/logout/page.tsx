"use client"

import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

export default function Logout() {

    const onSubmit = async () => {
        alert("logout");
    }

    return (
        <Card className="max-w-sm m-[1rem]">
            <CardHeader>
                <CardTitle className="text-2xl text-center">Are you sure you want to Logout ?</CardTitle>
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
