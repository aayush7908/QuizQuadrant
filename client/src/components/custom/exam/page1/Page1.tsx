"use client"

import { Input } from "@/components/ui/input"
import { Exam } from "@/lib/type/model/Exam"
import { Label } from "@/components/ui/label"

export function Page1({
    exam,
    changeExam
}: {
    exam: Exam,
    changeExam: Function
}) {

    const handleChangeTitle = ({ target }: { target: { value: string } }) => {
        const newExam = { ...exam } as Exam;
        newExam.title = target.value;
        changeExam(newExam);
    }

    const handleChangeDate = ({ target }: { target: { value: string } }) => {
        const newExam = { ...exam } as Exam;
        newExam.startDateTime = new Date(target.value);
        changeExam(newExam);
    }
    
    const handleChangeDuration = ({ target }: { target: { value: string } }) => {
        const newExam = { ...exam } as Exam;
        newExam.durationInMinutes = Number(target.value);
        changeExam(newExam);
    }

    return (
        <div className="flex flex-col gap-[2rem]">
            <div className="grid gap-2">
                <Label>Exam Title</Label>
                <Input
                    value={exam.title}
                    onChange={(e) => { handleChangeTitle(e) }}
                    type="text"
                    required
                />
            </div>
            <div className="grid sm:grid-cols-2 gap-[2rem]">
                <div className="grid gap-2">
                    <Label>Start Date & Time</Label>
                    <Input
                        value={exam.startDateTime.toISOString().slice(0, 16)}
                        onChange={(e) => { handleChangeDate(e) }}
                        type="datetime-local" min={(new Date()).toISOString().slice(0, 16)}
                        required
                    />
                </div>
                <div className="grid gap-2">
                    <Label>Exam Duration (in minutes)</Label>
                    <Input
                        value={exam.durationInMinutes}
                        onChange={(e) => { handleChangeDuration(e) }}
                        type="number"
                        required
                    />
                </div>
            </div>
        </div>
    )
}
