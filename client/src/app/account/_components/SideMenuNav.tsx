"use client"

import Link from "next/link";
import { usePathname } from "next/navigation"
import { useContext } from "react";
import {
    Bookmark,
    CircleUser,
    FileCheck,
    FileText,
    Folder,
    MailQuestion,
    Users
} from "lucide-react"
import {
    validateAdminAccess,
    validateTeacherAccess,
    validateUserAccess
} from "@/app/_lib/user-access-validation-utils";
import { AuthContext } from "@/context/auth/AuthContext";

export function SideMenuNav() {

    const path = usePathname();
    const { user } = useContext(AuthContext);

    return (
        <>
            {
                user && (
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
                                (validateAdminAccess(user) || validateUserAccess(user)) && (
                                    <li>
                                        <Link
                                            href="/account/saved-questions"
                                            className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/saved-questions" && "bg-muted text-primary"}`}
                                        >
                                            <Bookmark />
                                            <span>Saved Questions</span>
                                        </Link>
                                    </li>
                                )
                            }
                            {
                                (validateAdminAccess(user) || validateTeacherAccess(user)) && (
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
                                        <li>
                                            <Link
                                                href="/account/draft-questions"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/draft-questions" && "bg-muted text-primary"}`}
                                            >
                                                <FileText />
                                                <span>Draft Questions</span>
                                            </Link>
                                        </li>
                                    </>
                                )
                            }
                            {
                                validateAdminAccess(user) && (
                                    <>
                                        <li>
                                            <Link
                                                href="/account/admin/subject-and-subtopic"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/admin/subject-and-subtopic" && "bg-muted text-primary"}`}
                                            >
                                                <Folder />
                                                <span>Subject & Subtopic</span>
                                            </Link>
                                        </li>
                                        <li>
                                            <Link
                                                href="/account/admin/users"
                                                className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/admin/users" && "bg-muted text-primary"}`}
                                            >
                                                <Users />
                                                <span>Users</span>
                                            </Link>
                                        </li>
                                    </>
                                )
                            }
                            {
                                !validateUserAccess(user) && (
                                    <li>
                                        <Link
                                            href="/account/verify-email"
                                            className={`flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary ${path === "/account/verify-email" && "bg-muted text-primary"}`}
                                        >
                                            <MailQuestion />
                                            <span>Verify Email</span>
                                        </Link>
                                    </li>
                                )
                            }
                        </ul>
                    </nav>
                )
            }
        </>
    );
}