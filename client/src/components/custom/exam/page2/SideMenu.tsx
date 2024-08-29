"use client"

import {
    ChevronsUp,
} from "lucide-react"
import { Button } from "@/components/ui/button"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { SideMenuNav } from "./SideMenuNav"
import { Exam } from "@/lib/type/model/Exam"
import { Question } from "@/lib/type/model/Question"

export function SideMenu({
    questionIndex,
    changeQuestionIndex,
    exam,
    changeExam
}: {
    questionIndex: number,
    changeQuestionIndex: Function,
    exam: Exam,
    changeExam: Function
}) {
    return (
        <>
            <nav className="h-[calc(100%-8rem)] w-[17rem] font-medium border-r-2 p-4 hidden md:block fixed top-[4rem] overflow-y-auto bg-muted">
                <SideMenuNav
                    questionIndex={questionIndex}
                    changeQuestionIndex={changeQuestionIndex}
                    exam={exam}
                    changeExam={changeExam}
                />
            </nav>
            <div className="fixed flex z-10 justify-center bottom-[0.5rem] h-[2.5rem] text-center md:hidden">
                <Sheet>
                    <SheetTrigger asChild>
                        <Button className="px-2 ms-5 z-50">
                            <ChevronsUp />
                        </Button>
                    </SheetTrigger>
                    <SheetContent side="bottom">
                        <SideMenuNav
                            questionIndex={questionIndex}
                            changeQuestionIndex={changeQuestionIndex}
                            exam={exam}
                            changeExam={changeExam}
                        />
                    </SheetContent>
                </Sheet>
            </div>
        </>
    )
}
