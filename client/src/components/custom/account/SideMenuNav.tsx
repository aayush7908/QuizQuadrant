"use client"

import { Badge } from "@/components/ui/badge";
import { AuthContext } from "@/context/auth/AuthContext";
import {
    AlarmClock,
    BookCheck,
    BookText,
    CircleCheckBig,
    CircleUser,
    FileCheck,
    FileText,
    Folder,
    MailQuestion,
    Radio,
    Trash2
} from "lucide-react"
import Link from "next/link";
import { usePathname } from "next/navigation"
import { useContext } from "react";

export function SideMenuNav() {

    const path = usePathname();
    const { user } = useContext(AuthContext);

    return (
        <nav className="grid gap-1 mt-[2rem] md:mt-0">
            <ul className="grid gap-1">
                <li>
                    <Link
                        href="/account/profile"
                        className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/profile" && "bg-muted text-primary"}`}
                    >
                        <CircleUser />
                        <span>Profile</span>
                    </Link>
                </li>
                {
                    (user?.role === "ADMIN" || user?.isEmailVerified) ? (
                        <>
                            {
                                (user.role === "STUDENT" || user.role === "ADMIN") && (
                                    <>
                                        {/* <li>
                                            <Link
                                                href="/account/ongoing-exams"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/ongoing-exams" && "bg-muted text-primary"}`}
                                            >
                                                <Radio />
                                                <span>Ongoing Exams</span>
                                                <Badge className="ml-auto flex h-6 w-6 shrink-0 items-center justify-center rounded-full">
                                                    6
                                                </Badge>
                                            </Link>
                                        </li>
                                        <li>
                                            <Link
                                                href="/account/future-exams"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/future-exams" && "bg-muted text-primary"}`}
                                            >
                                                <AlarmClock />
                                                <span>Future Exams</span>
                                            </Link>
                                        </li>
                                        <li>
                                            <Link
                                                href="/account/past-exams"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/past-exams" && "bg-muted text-primary"}`}
                                            >
                                                <CircleCheckBig />
                                                <span>Past Exams</span>
                                            </Link>
                                        </li> */}
                                    </>
                                )
                            }
                            {
                                (user.role === "TEACHER" || user.role === "ADMIN") && (
                                    <>
                                        <li>
                                            <Link
                                                href="/account/questions-created"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/questions-created" && "bg-muted text-primary"}`}
                                            >
                                                <FileCheck />
                                                <span>Questions Created</span>
                                            </Link>
                                        </li>
                                        {/* <li>
                                            <Link
                                                href="/account/exams-created"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/exams-created" && "bg-muted text-primary"}`}
                                            >
                                                <BookCheck />
                                                <span>Exams Created</span>
                                            </Link>
                                        </li> */}
                                        <li>
                                            <Link
                                                href="/account/draft-questions"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/draft-questions" && "bg-muted text-primary"}`}
                                            >
                                                <FileText />
                                                <span>Draft Questions</span>
                                            </Link>
                                        </li>
                                        {/* <li>
                                            <Link
                                                href="/account/draft-exams"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/draft-exams" && "bg-muted text-primary"}`}
                                            >
                                                <BookText />
                                                <span>Draft Exams</span>
                                            </Link>
                                        </li> */}
                                    </>
                                )
                            }
                            {
                                user.role === "ADMIN" && (
                                    <>
                                        <li>
                                            <Link
                                                href="/account/subject-and-subtopic"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/subject-and-subtopic" && "bg-muted text-primary"}`}
                                            >
                                                <Folder />
                                                <span>Subject & Subtopic</span>
                                            </Link>
                                        </li>
                                    </>
                                )
                            }
                            <li>
                                <Link
                                    href="/account/delete-account"
                                    className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/delete-account" && "bg-muted text-primary"}`}
                                >
                                    <Trash2 />
                                    <span>Delete Account</span>
                                </Link>
                            </li>
                        </>
                    ) : (
                        <>
                            <li>
                                <Link
                                    href="/account/verify-email"
                                    className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/verify-email" && "bg-muted text-primary"}`}
                                >
                                    <MailQuestion />
                                    <span>Verify Email</span>
                                </Link>
                            </li>
                        </>
                    )
                }
            </ul>
        </nav>
    );
}