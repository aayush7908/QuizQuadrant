"use client"

import Link from "next/link"
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { useState } from "react"
import EmailForm from "@/components/custom/auth/forgot-password/EmailForm";
import OtpForm from "@/components/custom/auth/forgot-password/OtpForm";
import PasswordForm from "@/components/custom/auth/forgot-password/PasswordForm";
export default function ForgotPassword() {

    const [page, setPage] = useState<number>(1);
    const [email, setEmail] = useState<string>("");

    const changePage = (newPage: number) => {
        setPage(newPage);
    }

    const changeEmail = (newEmail: string) => {
        setEmail(newEmail);
    }

    return (
        <Card className="max-w-sm m-[1rem]">
            <CardHeader>
                <CardTitle className="text-2xl">Forgot Password</CardTitle>
                <CardDescription>
                    <span className={`${page === 1 ? "block" : "hidden"}`}>Enter your email below to verify your account</span>
                    <span className={`${page === 2 ? "block" : "hidden"}`}>Enter OTP sent to your email address</span>
                    <span className={`${page === 3 ? "block" : "hidden"}`}>Reset password for your account</span>
                </CardDescription>
            </CardHeader>
            <CardContent className={`${page === 1 ? "block" : "hidden"}`}>
                <EmailForm page={page} changePage={changePage} changeEmail={changeEmail} />
            </CardContent>
            <CardContent className={`${page === 2 ? "block" : "hidden"}`}>
                <OtpForm page={page} changePage={changePage} email={email} />
            </CardContent>
            <CardContent className={`${page === 3 ? "block" : "hidden"}`}>
                <PasswordForm page={page} changePage={changePage} email={email} />
            </CardContent>
            <CardFooter>
                <div className="text-center text-sm flex w-full justify-center gap-2">
                    <Link href="/auth/login" className="underline">
                        Login
                    </Link>
                    <p>|</p>
                    <Link href="/auth/register" className="underline">
                        Register
                    </Link>
                </div>
            </CardFooter>
        </Card>
    )
}