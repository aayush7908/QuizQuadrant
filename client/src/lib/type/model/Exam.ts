import { Question } from "./Question";
import { User } from "./User";

type Exam = {
    id: string,
    title: string,
    startDateTime: Date,
    durationInMinutes: number,
    isResultGenerated: boolean,
    totalMarks: number,
    lastModifiedOn: Date,
    createdBy: User,
    questions: Array<Question>,
    candidates: Array<User>
};

export type {
    Exam
}