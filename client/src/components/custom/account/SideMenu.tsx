"use client"

import { Button } from "@/components/ui/button"
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
