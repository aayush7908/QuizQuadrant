"use client"

import Link from "next/link"
import {
    AlarmClock,
    Bell,
    ChevronsUp,
    CircleCheckBig,
    CircleUser,
    FileCheck,
    Home,
    LineChart,
    Menu,
    Package,
    Package2,
    PlusSquare,
    Radio,
    Search,
    ShoppingCart,
    Users,
} from "lucide-react"

import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Input } from "@/components/ui/input"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { usePathname } from "next/navigation"
import { SideMenuNav } from "./SideMenuNav"

export function SideMenu() {

    const pathname = usePathname();

    return (
        <>
            <nav className="h-[calc(100%-8rem)] w-[17rem] font-medium border-r-2 p-2 lg:p-4 hidden md:block fixed top-[4rem] overflow-y-auto bg-muted">
                <SideMenuNav />
            </nav>
            <div className="fixed flex z-10 justify-center bottom-[0.5rem] h-[2.5rem] text-center md:hidden">
                <Sheet>
                    <SheetTrigger asChild>
                        <Button className="px-2 ms-5 z-50">
                            <ChevronsUp />
                        </Button>
                    </SheetTrigger>
                    <SheetContent side="bottom">
                        <SideMenuNav />
                    </SheetContent>
                </Sheet>
            </div>
        </>
    )
}
