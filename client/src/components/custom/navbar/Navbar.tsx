"use client"

import { Button } from "@/components/ui/button"
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { AuthContext } from "@/context/auth/AuthContext"
import { CircleUser, LogIn, LogOut, Menu, Package2, Search } from "lucide-react"
import Link from "next/link"
import { useContext } from "react"

export default function Navbar() {

    const { user } = useContext(AuthContext);

    return (
        <header className="fixed z-10 top-0 w-full h-[4rem] border-b-2 bg-muted px-4 md:px-6">
            <nav className="h-full w-full">
                <ul className="h-full w-full flex justify-between items-center">
                    <li>
                        <Link href={"/"}>
                            <h1 className="font-bold text-xl">QuizQuadrant</h1>
                        </Link>
                    </li>
                    <li className="hidden md:flex">
                        <ul className="flex gap-5 justify-end items-center">
                            {
                                user && (
                                    <li>
                                        <DropdownMenu>
                                            <DropdownMenuTrigger asChild>
                                                <Button variant="secondary" size="icon" className="rounded-full">
                                                    <CircleUser />
                                                </Button>
                                            </DropdownMenuTrigger>
                                            <DropdownMenuContent align="end">
                                                <Link href={"/account/profile"}>
                                                    <DropdownMenuItem className="cursor-pointer">
                                                        Profile
                                                    </DropdownMenuItem>
                                                </Link>
                                                <DropdownMenuSeparator />
                                                <Link href={"/auth/logout"}>
                                                    <DropdownMenuItem className="cursor-pointer">
                                                        Logout
                                                    </DropdownMenuItem>
                                                </Link>
                                            </DropdownMenuContent>
                                        </DropdownMenu>
                                    </li>
                                )
                            }
                            {
                                !user && (
                                    <li>
                                        <Link href={"/auth/login"}>
                                            <LogIn />
                                        </Link>
                                    </li>
                                )
                            }
                        </ul>
                    </li>
                    <li className="md:hidden">
                        <Sheet>
                            <SheetTrigger asChild>
                                <Button
                                    variant="outline"
                                    size="icon"
                                    className="shrink-0 top-0"
                                >
                                    <Menu className="h-5 w-5" />
                                </Button>
                            </SheetTrigger>
                            <SheetContent side="left">
                                <nav className="grid gap-6 text-lg font-medium mt-[2rem]">
                                    <Link
                                        href="/u/abcd-abcd-abcd"
                                        className="flex gap-3 items-center text-muted-foreground hover:text-foreground"
                                    >
                                        <CircleUser />
                                        <span>Profile</span>
                                    </Link>
                                    <Link
                                        href="/auth/logout"
                                        className="flex gap-3 items-center text-muted-foreground hover:text-foreground"
                                    >
                                        <LogOut />
                                        <span>Logout</span>
                                    </Link>
                                </nav>
                            </SheetContent>
                        </Sheet>
                    </li>
                </ul>
            </nav>
        </header>
    );
}
