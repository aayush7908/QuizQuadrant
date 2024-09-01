import { Button } from "@/components/ui/button";
import { Exam } from "@/lib/type/model/Exam";
import { Option } from "@/lib/type/model/Option";
import { Question } from "@/lib/type/model/Question";
import { Solution } from "@/lib/type/model/Solution";
import { Subject } from "@/lib/type/model/Subject";
import { Subtopic } from "@/lib/type/model/Subtopic";
import { PlusSquare } from "lucide-react";

export function SideMenuNav({
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

    const handleAddQuestion = (() => {
        const newExam = { ...exam } as Exam;
        const newQuestion = {
            id: "",
            type: "",
            isPublic: false,
            positiveMarks: 0,
            negativeMarks: 0,
            statement: "",
            imageUrl: undefined,
            subtopic: {
                id: "",
                subject: {
                    id: ""
                } as Subject
            } as Subtopic,
            options: [
                {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                } as Option,
                {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                } as Option,
                {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                } as Option,
                {
                    statement: "",
                    imageUrl: undefined,
                    isCorrect: false
                } as Option
            ],
            solution: {
                statement: "",
                imageUrl: undefined
            } as Solution
        } as Question;
        newExam.questions.push(newQuestion);
        changeExam(newExam);
        changeQuestionIndex(questionIndex + 1);
    })

    const handleChangeQuestion = (newIndex: number) => {
        changeQuestionIndex(newIndex);
    }

    return (
        <>
            <div className="flex justify-center">
                <Button onClick={handleAddQuestion} className="flex gap-2">
                    <PlusSquare />
                    <span>Add Question</span>
                </Button>
            </div>
            <div className="grid grid-cols-4 gap-3 mt-5 h-[20rem] overflow-auto sm:h-auto">
                {
                    exam.questions.map((question: Question, index: number) => {
                        return (
                            <Button
                                key={index}
                                onClick={() => { handleChangeQuestion(index) }}
                                className={`${questionIndex !== index && "bg-blue-600"}`}
                            >
                                {index + 1}
                            </Button>
                        )
                    })
                }
            </div>
        </>
    );
}