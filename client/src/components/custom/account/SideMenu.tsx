"use client"

import Link from "next/link"
import {
    AlarmClock,
    Bell,
    CircleCheckBig,
    CircleUser,
    FileCheck,
    Home,
    LineChart,
    Menu,
    Package,
    Package2,
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
            <nav className="h-full w-[17rem] font-medium border-r-2 p-2 lg:p-4 hidden md:block fixed top-[4rem]">
                <SideMenuNav />
            </nav>
            <div className="fixed bottom-0 w-full p-1 h-[2.5rem] text-center md:hidden">
                <Sheet>
                    <SheetTrigger asChild>
                        <Button className="h-full w-full">
                            <span>Show Menu</span>
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
