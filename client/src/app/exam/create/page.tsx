"use client"

import { Page1 } from "@/components/custom/exam/create/page1/Page1";
import { Page2 } from "@/components/custom/exam/create/page2/Page2";
import { Page3 } from "@/components/custom/exam/create/page3/Page3";
import { Button } from "@/components/ui/button";
import { MoveLeft, SquareChevronLeft, SquareChevronRight } from "lucide-react";
import { useState } from "react";

export default function CreateExam() {

    const [page, setPage] = useState<number>(1);

    return (
        <div className="h-full w-full">
            <div className="h-[calc(100%-4rem)]">
                {
                    page === 1 && (
                        <div className="h-full py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
                            <Page1 />
                        </div>
                    )
                }
                {
                    page === 2 && (
                        <div className="h-full">
                            <Page2 />
                        </div>
                    )
                }
                {
                    page === 3 && (
                        <div className="h-full py-[2rem] grid gap-[1rem] px-[1rem] md:px-[3rem] lg:px-[12rem]">
                            <Page3 />
                        </div>
                    )
                }
            </div>
            <div className="fixed h-[4rem] w-full px-5 flex gap-3 justify-end md:justify-between items-center bg-muted border-t-2">
                <Button className="px-2 sm:px-3" disabled={page === 1} onClick={() => { setPage(page => (page - 1)) }}>
                    <SquareChevronLeft className="sm:hidden" />
                    <span className="hidden sm:block">Prev</span>
                </Button>
                <Button className="px-2 sm:px-3" disabled={page === 3} onClick={() => { setPage(page => (page + 1)) }}>
                    <SquareChevronRight className="sm:hidden" />
                    <span className="hidden sm:block">Next</span>
                </Button>
            </div>
        </div>
    );
}
